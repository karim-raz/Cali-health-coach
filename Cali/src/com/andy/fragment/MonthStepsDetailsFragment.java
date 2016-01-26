package com.andy.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.cali.R;
import com.andy.entities.User;
import com.andy.sqlite.UserBDD;
import com.andy.utils.PedometerSettings;
import com.andy.utils.RippleBackground;
import com.andy.utils.StepService;
import com.andy.utils.Utils;
import com.gc.materialdesign.views.ButtonFloat;

public class MonthStepsDetailsFragment extends Fragment {
	public static final String TAG = "Pedometer";
	public SharedPreferences mSettings;
	public PedometerSettings mPedometerSettings;
	public Utils mUtils;
	ButtonFloat share;
	public TextView mStepValueView;

	public TextView mCaloriesValueView;

	public int mStepValue;

	public int mCaloriesValue;
	public float mDesiredPaceOrSpeed;
	public int mMaintain;

	public boolean mQuitting = false; // Set when user selected Quit from menu,
										// can be used by onPause, onStop,
										// onDestroy

	/**
	 * True, when service is running.
	 */
	public boolean mIsRunning;
	String nameuserfinal;
	TextView My;
	UserBDD userBDD;
	List<User> l;
	ImageView iv;

	String id;
	View root;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mStepValue = 0;

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		root = inflater.inflate(R.layout.monthsteps_layout, container, false);

		l = new ArrayList<User>();
		userBDD = new UserBDD(getActivity());
		userBDD.open();

		l = userBDD.selectAll();

		userBDD.close();
		getActivity().getActionBar().setIcon(R.drawable.cursor_red);
		getActivity().getActionBar().setTitle("Steps");
		iv = (ImageView) root.findViewById(R.id.Mypic1m);

		byte[] bitmapdata = l.get(0).getLogo();
		// if bitmapdata is the byte array then getting bitmap goes like this

		Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0,
				bitmapdata.length);
		iv.setImageBitmap(bitmap);
	
		My = (TextView) root.findViewById(R.id.todaym);
		My.setText(l.get(0).getName().toString());
		share = (ButtonFloat) root.findViewById(R.id.buttonFloat22m);
		final RippleBackground rippleBackground = (RippleBackground) root
				.findViewById(R.id.contentm);

		rippleBackground.startRippleAnimation();
		mUtils = Utils.getInstance();
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent sharingIntent = new Intent(Intent.ACTION_SEND);
				sharingIntent.setType("*/*");
				String sharingText = "I walked " + mStepValue
						+ " Steps Today on Cali";

				// Bitmap icon = BitmapFactory.decodeResource(getActivity()
				// .getResources(), R.drawable.logoapp);
				// ;
				//
				// ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				// icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
				// File f = new File(Environment.getExternalStorageDirectory()
				// + File.separator + "temporary_fil.jpg");
				// try {
				// f.createNewFile();
				// FileOutputStream fo = new FileOutputStream(f);
				// fo.write(bytes.toByteArray());
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				// sharingIntent.putExtra(Intent.EXTRA_STREAM,
				// Uri.parse("file:///sdcard/temporary_fil.jpg"));
				sharingIntent.putExtra(Intent.EXTRA_TEXT, sharingText);
				startActivity(Intent
						.createChooser(sharingIntent, "Share using"));
			}
		});
		return root;

	}

	@Override
	public void onStart() {
		Log.i(TAG, "[ACTIVITY] onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.i(TAG, "[ACTIVITY] onResume");
		super.onResume();

		mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity()
				.getApplicationContext());
		mPedometerSettings = new PedometerSettings(mSettings);

		mUtils.setSpeak(mSettings.getBoolean("speak", false));

		// Read from preferences if the service was running on the last onPause
		mIsRunning = mPedometerSettings.isServiceRunning();

		// Start the service if this is considered to be an application start
		// (last onPause was long ago)
		if (!mIsRunning && mPedometerSettings.isNewStart()) {
			startStepService();
			bindStepService();
		} else if (mIsRunning) {
			bindStepService();
		}

		mPedometerSettings.clearServiceRunning();
		mStepValueView = (TextView) root.findViewById(R.id.stepsvaluem);
		mCaloriesValueView = (TextView) root.findViewById(R.id.textView2261m);

	}

	@Override
	public void onPause() {
		Log.i(TAG, "[ACTIVITY] onPause");
		if (mIsRunning) {
			unbindStepService();
		}
		if (mQuitting) {
			mPedometerSettings.saveServiceRunningWithNullTimestamp(mIsRunning);
		} else {
			mPedometerSettings.saveServiceRunningWithTimestamp(mIsRunning);
		}

		super.onPause();
		savePaceSetting();
	}

	@Override
	public void onStop() {
		Log.i(TAG, "[ACTIVITY] onStop");
		super.onStop();
		bindStepService();
	}

	public void onDestroy() {
		Log.i(TAG, "[ACTIVITY] onDestroy");
		super.onDestroy();

	}

	protected void onRestart() {
		Log.i(TAG, "[ACTIVITY] onRestart");
		super.onDestroy();
	}

	private void savePaceSetting() {
		mPedometerSettings.savePaceOrSpeedSetting(mMaintain,
				mDesiredPaceOrSpeed);
	}

	public StepService mService;

	public ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mService = ((StepService.StepBinder) service).getService();

			mService.registerCallback(mCallback);
			mService.reloadSettings();

		}

		public void onServiceDisconnected(ComponentName className) {
			mService = null;
		}
	};

	public void startStepService() {
		if (!mIsRunning) {
			Log.i(TAG, "[SERVICE] Start");
			mIsRunning = true;
			new Intent(getActivity(), StepService.class);
		}
	}

	public void bindStepService() {
		Log.i(TAG, "[SERVICE] Bind");
		getActivity().bindService(
				new Intent(getActivity().getApplicationContext(),
						StepService.class), mConnection,
				Context.BIND_AUTO_CREATE + Context.BIND_DEBUG_UNBIND);
	}

	public void unbindStepService() {
		Log.i(TAG, "[SERVICE] Unbind");
		getActivity().unbindService(mConnection);
	}

	public void stopStepService() {
		Log.i(TAG, "[SERVICE] Stop");
		if (mService != null) {
			Log.i(TAG, "[SERVICE] stopService");
			getActivity().stopService(
					new Intent(getActivity().getApplicationContext(),
							StepService.class));

		}
		mIsRunning = false;
	}

	public void resetValues(boolean updateDisplay) {
		if (mService != null && mIsRunning) {
			mService.resetValues();
		} else {
			mStepValueView.setText("0");

			mCaloriesValueView.setText("0");
			SharedPreferences state = getActivity().getSharedPreferences(
					"state", 0);
			SharedPreferences.Editor stateEditor = state.edit();
			if (updateDisplay) {
				stateEditor.putInt("steps", 0);

				stateEditor.putFloat("calories", 0);
				stateEditor.commit();
			}
		}
	}

	// private static final int MENU_SETTINGS = 8;
	// private static final int MENU_QUIT = 9;
	//
	// private static final int MENU_PAUSE = 1;
	// private static final int MENU_RESUME = 2;
	// private static final int MENU_RESET = 3;

	// /* Creates the menu items */
	// public boolean onPrepareOptionsMenu(Menu menu) {
	// menu.clear();
	// if (mIsRunning) {
	// menu.add(0, MENU_PAUSE, 0, "")
	// .setIcon(android.R.drawable.ic_media_pause)
	// .setShortcut('1', 'p');
	// }
	// else {
	// menu.add(0, MENU_RESUME, 0, "")
	// .setIcon(android.R.drawable.ic_media_play)
	// .setShortcut('1', 'p');
	// }
	// menu.add(0, MENU_RESET, 0, "")
	// .setIcon(android.R.drawable.ic_menu_close_clear_cancel)
	// .setShortcut('2', 'r');
	// // menu.add(0, MENU_SETTINGS, 0,"")
	// // .setIcon(android.R.drawable.ic_menu_preferences)
	// // .setShortcut('8', 's')
	// // .setIntent(new Intent(this, Settings.class));
	// menu.add(0, MENU_QUIT, 0, "")
	// .setIcon(android.R.drawable.ic_lock_power_off)
	// .setShortcut('9', 'q');
	// return true;
	// }

	/* Handles item selections */
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case MENU_PAUSE:
	// unbindStepService();
	// stopStepService();
	// return true;
	// case MENU_RESUME:
	// startStepService();
	// bindStepService();
	// return true;
	// case MENU_RESET:
	// resetValues(true);
	// return true;
	// case MENU_QUIT:
	// resetValues(false);
	// unbindStepService();
	// stopStepService();
	// mQuitting = true;
	// finish();
	// return true;
	// }
	// return false;
	// }

	// TODO: unite all into 1 type of message
	public StepService.ICallback mCallback = new StepService.ICallback() {
		public void stepsChanged(int value) {
			mHandler.sendMessage(mHandler.obtainMessage(STEPS_MSG, value, 0));
		}

		public void paceChanged(int value) {
			mHandler.sendMessage(mHandler.obtainMessage(PACE_MSG, value, 0));
		}

		public void distanceChanged(float value) {
			mHandler.sendMessage(mHandler.obtainMessage(DISTANCE_MSG,
					(int) (value * 1000), 0));
		}

		public void speedChanged(float value) {
			mHandler.sendMessage(mHandler.obtainMessage(SPEED_MSG,
					(int) (value * 1000), 0));
		}

		public void caloriesChanged(float value) {
			mHandler.sendMessage(mHandler.obtainMessage(CALORIES_MSG,
					(int) (value), 0));
		}
	};

	public static final int STEPS_MSG = 1;
	public static final int PACE_MSG = 2;
	public static final int DISTANCE_MSG = 3;
	public static final int SPEED_MSG = 4;
	public static final int CALORIES_MSG = 5;

	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STEPS_MSG:

				mStepValue = (int) msg.arg1;
				mStepValueView.setText("" + mStepValue + " Steps");

				break;

			case CALORIES_MSG:
				mCaloriesValue = msg.arg1;
				if (mCaloriesValue <= 0) {
					mCaloriesValueView.setText("0");
				} else {
					mCaloriesValueView.setText("" + (int) mCaloriesValue
							+ " Calories");
				}
				break;

			}
		}

	};

}
