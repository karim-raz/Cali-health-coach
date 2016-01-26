/*
 *  Pedometer - Android App
 *  Copyright (C) 2009 Levente Bagi
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.andy.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.andy.cali.R;
import com.andy.cali.activity.StepsActivity;

/**
 * This is an example of implementing an application service that runs locally
 * in the same process as the application. The {@link StepServiceController} and
 * {@link StepServiceBinding} classes show how to interact with the service.
 * 
 * <p>
 * Notice the use of the {@link NotificationManager} when interesting things
 * happen in the service. This is generally how background services should
 * interact with the user, rather than doing something more disruptive such as
 * calling startActivity().
 */
public class StepService extends Service {
	private static final String TAG = "name.bagi.levente.pedometer.StepService";
	private SharedPreferences mSettings;
	private PedometerSettings mPedometerSettings;
	private SharedPreferences mState;
	private SharedPreferences.Editor mStateEditor;
	private Utils mUtils;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private StepDetector mStepDetector;
	// private StepBuzzer mStepBuzzer; // used for debugging
	private StepDisplayer mStepDisplayer;

	private CaloriesNotifier mCaloriesNotifier;

	private PowerManager.WakeLock wakeLock;
	private NotificationManager mNM;

	private int mSteps;
	private int mPace;
	private float mDistance;
	private float mSpeed;
	private float mCalories;

	/**
	 * Class for clients to access. Because we know this service always runs in
	 * the same process as its clients, we don't need to deal with IPC.
	 */
	public class StepBinder extends Binder {
		public StepService getService() {
			return StepService.this;
		}
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "[SERVICE] onCreate");
		super.onCreate();

		mNM = (NotificationManager) getApplication().getSystemService(
				Context.NOTIFICATION_SERVICE);
		showNotification();

		// Load settings
		mSettings = PreferenceManager.getDefaultSharedPreferences(this);
		mPedometerSettings = new PedometerSettings(mSettings);
		mState = getSharedPreferences("state", 0);

		mUtils = Utils.getInstance();
		mUtils.setService(this);
		mUtils.initTTS();

		acquireWakeLock();

		// Start detecting
		mStepDetector = new StepDetector();
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		registerDetector();

		// Register our receiver for the ACTION_SCREEN_OFF action. This will
		// make our receiver
		// code be called whenever the phone enters standby mode.
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		registerReceiver(mReceiver, filter);

		mStepDisplayer = new StepDisplayer(mPedometerSettings, mUtils);
		mStepDisplayer.setSteps(mSteps = mState.getInt("steps", 0));
		mStepDisplayer.addListener(mStepListener);
		mStepDetector.addStepListener(mStepDisplayer);

		mCaloriesNotifier = new CaloriesNotifier(mCaloriesListener,
				mPedometerSettings, mUtils);
		mCaloriesNotifier.setCalories(mCalories = mState
				.getFloat("calories", 0));
		mStepDetector.addStepListener(mCaloriesNotifier);

		// Used when debugging:
		// mStepBuzzer = new StepBuzzer(this);
		// mStepDetector.addStepListener(mStepBuzzer);

		// Start voice
		reloadSettings();

		// Tell the user we started.
		// Toast.makeText(this, getText(R.string.started),
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.i(TAG, "[SERVICE] onStart");
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "[SERVICE] onDestroy");
		mUtils.shutdownTTS();

		// Unregister our receiver.
		unregisterReceiver(mReceiver);
		unregisterDetector();

		mStateEditor = mState.edit();
		mStateEditor.putInt("steps", mSteps);
		mStateEditor.putInt("pace", mPace);
		mStateEditor.putFloat("distance", mDistance);
		mStateEditor.putFloat("speed", mSpeed);
		mStateEditor.putFloat("calories", mCalories);
		mStateEditor.commit();

		// mNM.cancel(R.string.app_name);

		wakeLock.release();

		super.onDestroy();

		// Stop detecting
		mSensorManager.unregisterListener(mStepDetector);

		// Tell the user we stopped.
		// Toast.makeText(this, getText(R.string.stopped),
		// Toast.LENGTH_SHORT).show();
	}

	private void registerDetector() {
		mSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER /*
															 * | Sensor.
															 * TYPE_MAGNETIC_FIELD
															 * | Sensor.
															 * TYPE_ORIENTATION
															 */);
		mSensorManager.registerListener(mStepDetector, mSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	private void unregisterDetector() {
		mSensorManager.unregisterListener(mStepDetector);
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "[SERVICE] onBind");
		return mBinder;
	}

	/**
	 * Receives messages from activity.
	 */
	private final IBinder mBinder = new StepBinder();

	public interface ICallback {
		public void stepsChanged(int value);

		public void paceChanged(int value);

		public void distanceChanged(float value);

		public void speedChanged(float value);

		public void caloriesChanged(float value);
	}

	private ICallback mCallback;

	public void registerCallback(ICallback cb) {
		mCallback = cb;
		// mStepDisplayer.passValue();
		// mPaceListener.passValue();
	}

	private int mDesiredPace;
	private float mDesiredSpeed;

	/**
	 * Called by activity to pass the desired pace value, whenever it is
	 * modified by the user.
	 * 
	 * @param desiredPace
	 */

	/**
	 * Called by activity to pass the desired speed value, whenever it is
	 * modified by the user.
	 * 
	 * @param desiredSpeed
	 */

	public void reloadSettings() {
		mSettings = PreferenceManager.getDefaultSharedPreferences(this);

		if (mStepDetector != null) {
			mStepDetector.setSensitivity(Float.valueOf(mSettings.getString(
					"sensitivity", "10")));
		}

		if (mStepDisplayer != null)
			mStepDisplayer.reloadSettings();

		if (mCaloriesNotifier != null)
			mCaloriesNotifier.reloadSettings();
	}

	public void resetValues() {
		mStepDisplayer.setSteps(0);

		mCaloriesNotifier.setCalories(0);
	}

	/**
	 * Forwards pace values from PaceNotifier to the activity.
	 */
	private StepDisplayer.Listener mStepListener = new StepDisplayer.Listener() {
		public void stepsChanged(int value) {
			mSteps = value;
			passValue();
		}

		public void passValue() {
			if (mCallback != null) {
				mCallback.stepsChanged(mSteps);
			}
		}
	};
	/**
	 * Forwards pace values from PaceNotifier to the activity.
	 */

	/**
	 * Forwards distance values from DistanceNotifier to the activity.
	 */

	/**
	 * Forwards speed values from SpeedNotifier to the activity.
	 */

	/**
	 * Forwards calories values from CaloriesNotifier to the activity.
	 */
	private CaloriesNotifier.Listener mCaloriesListener = new CaloriesNotifier.Listener() {
		public void valueChanged(float value) {
			mCalories = value;
			passValue();
		}

		public void passValue() {
			if (mCallback != null) {
				mCallback.caloriesChanged(mCalories);
			}
		}
	};

	/**
	 * Show a notification while this service is running.
	 */

	private void showNotification() {

		// int requestID = (int) System.currentTimeMillis();

		// // Uri alarmSound = getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		// NotificationManager mNotificationManager =(NotificationManager)
		// getSystemService(NOTIFICATION_SERVICE);
		//
		// Intent notificationIntent = new Intent(getApplicationContext(),
		// DayStepsDetails.class);
		//
		// notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
		// Intent.FLAG_ACTIVITY_SINGLE_TOP);
		//
		//
		// PendingIntent contentIntent =
		// PendingIntent.getActivity(getApplicationContext(),
		// (int)System.currentTimeMillis(),notificationIntent,
		// PendingIntent.FLAG_UPDATE_CURRENT);
		// notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// NotificationCompat.Builder mBuilder = new
		// NotificationCompat.Builder(getApplicationContext())
		// .setSmallIcon(R.drawable.homess)
		// .setContentTitle("Cali")
		// .setStyle(new NotificationCompat.BigTextStyle()
		// .bigText("Counting your Steps"))
		// .setContentText("Counting your Steps").setAutoCancel(true);
		// //mBuilder.setSound(alarmSound);
		// mBuilder.setContentIntent(contentIntent);
		// mNotificationManager.notify(requestID , mBuilder.build());

		CharSequence text = getText(R.string.app_name);
		Notification notification = new Notification(R.drawable.homess, null,
				System.currentTimeMillis());
		notification.flags = Notification.FLAG_NO_CLEAR
				| Notification.FLAG_ONGOING_EVENT;
		Intent pedometerIntent = new Intent();
		pedometerIntent.setComponent(new ComponentName(this, StepsActivity.class));
		pedometerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				pedometerIntent, 0);
		notification.setLatestEventInfo(this, text, "Counting Steps",
				contentIntent);

		mNM.notify(R.string.app_name, notification);
	}

	// BroadcastReceiver for handling ACTION_SCREEN_OFF.
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Check action just to be on the safe side.
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				// Unregisters the listener and registers it again.
				StepService.this.unregisterDetector();
				StepService.this.registerDetector();
				if (mPedometerSettings.wakeAggressively()) {
					wakeLock.release();
					acquireWakeLock();
				}
			}
		}
	};

	private void acquireWakeLock() {
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		int wakeFlags;
		if (mPedometerSettings.wakeAggressively()) {
			wakeFlags = PowerManager.SCREEN_DIM_WAKE_LOCK
					| PowerManager.ACQUIRE_CAUSES_WAKEUP;
		} else if (mPedometerSettings.keepScreenOn()) {
			wakeFlags = PowerManager.SCREEN_DIM_WAKE_LOCK;
		} else {
			wakeFlags = PowerManager.PARTIAL_WAKE_LOCK;
		}
		wakeLock = pm.newWakeLock(wakeFlags, TAG);
		wakeLock.acquire();
	}

}
