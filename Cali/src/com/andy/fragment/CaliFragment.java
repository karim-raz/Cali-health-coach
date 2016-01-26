package com.andy.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.adapter.BaseInflaterAdapter;
import com.andy.adapter.CardItemData;
import com.andy.adapter.iflater.CardInflater0;
import com.andy.adapter.iflater.CardInflater1;
import com.andy.adapter.iflater.CardInflater2;
import com.andy.adapter.iflater.CardInflater3;
import com.andy.cali.R;
import com.andy.cali.activity.GoalWeightActivity;
import com.andy.cali.activity.LogDietsfirstActivity;
import com.andy.cali.activity.StepsActivity;
import com.andy.cali.activity.TipsActivity;
import com.andy.entities.Diet;
import com.andy.entities.Program;
import com.andy.sqlite.DietBDD;
import com.andy.sqlite.ProgBDD;
import com.andy.utils.Crouton;
import com.andy.utils.PedometerSettings;
import com.andy.utils.StepService;
import com.andy.utils.Utils;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

public class CaliFragment extends Fragment {

	BaseInflaterAdapter<CardItemData> adapter, adapter2, adapter3, adapter4;
	String toshow;
	ListView list;

	CardInflater1 card1;
	CardInflater0 card0;
	CardInflater2 card2;
	CardInflater3 card3;
	CardItemData data, data2, data4, data3;
	static final String TAG = "Pedometer";
	SharedPreferences mSettings;
	PedometerSettings mPedometerSettings;
	Utils mUtils;
	List<Diet> l1;
	DietBDD dt;
	ProgBDD pd;
	TextView mStepValueView;
	List<Diet> l;
	List<Program> l2;
	TextView mCaloriesValueView;
	String S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15,
			S16, S17, S18, S19;
	int mStepValue;

	int mCaloriesValue;
	float mDesiredPaceOrSpeed;
	int mMaintain;
	View root;
	boolean mQuitting = false; // Set when user selected Quit from menu,
								// can be used by onPause, onStop,
	SwingBottomInAnimationAdapter swingBottomInAnimationAdapter; // onDestroy
	boolean mIsRunning;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mStepValue = 0;
		getActivity().getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1E88E5")));
		getActivity().getActionBar().setTitle(
				getResources().getString(R.string.cali1));

		getActivity().getActionBar().setIcon(R.drawable.cursor_red);
		mUtils = Utils.getInstance();

		card0 = new CardInflater0();
		adapter = new BaseInflaterAdapter<CardItemData>(card0);
		card1 = new CardInflater1();
		adapter2 = new BaseInflaterAdapter<CardItemData>(card1);

		card2 = new CardInflater2();
		adapter3 = new BaseInflaterAdapter<CardItemData>(card2);

		card3 = new CardInflater3();
		adapter4 = new BaseInflaterAdapter<CardItemData>(card3);

	}

	@SuppressLint("NewApi")
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.cali_layout, container, false);

		S1 = getResources().getString(R.string.cali2);
		S2 = getResources().getString(R.string.cali3);
		S3 = getResources().getString(R.string.cali4);
		S4 = getResources().getString(R.string.cali5);
		S5 = getResources().getString(R.string.cali6);
		S6 = getResources().getString(R.string.cali2);
		S7 = getResources().getString(R.string.cali7);
		S8 = getResources().getString(R.string.cali3);
		S9 = getResources().getString(R.string.cali8);
		S10 = getResources().getString(R.string.cali9);
		S11 = getResources().getString(R.string.cali6);
		S12 = getResources().getString(R.string.cali2);
		S13 = getResources().getString(R.string.cali7);
		S14 = getResources().getString(R.string.cali3);
		S15 = getResources().getString(R.string.cali10);
		S16 = getResources().getString(R.string.cali11);
		S17 = getResources().getString(R.string.cali6);
		S18 = getResources().getString(R.string.cali2);
		S19 = getResources().getString(R.string.cali7);
		pd = new ProgBDD(getActivity());
		l2 = new ArrayList<Program>();
		pd.open();
		l2 = pd.selectAll();
		pd.close();

		if (l2.isEmpty()) {

			Diet d = new Diet("0", "0");
			DietBDD dtBDD1 = new DietBDD(getActivity());
			dtBDD1.open();
			dtBDD1.insertTop(d);
			dtBDD1.close();

			data4 = new CardItemData(R.drawable.goal, S1, "", "", "");

			adapter.addItem(data4, false);

			mStepValueView = (TextView) root.findViewById(R.id.text3);
			list = (ListView) root.findViewById(R.id.list1);
			swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
					adapter);
			swingBottomInAnimationAdapter.setAbsListView(list);

			list.setAdapter(swingBottomInAnimationAdapter);
			// setBottomAdapter();
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					switch (position) {
					case 0:

						// setBottomAdapter();

						// list.setAdapter(swingBottomInAnimationAdapter);
						Intent intent = new Intent(getActivity(),
								GoalWeightActivity.class);

						startActivityForResult(intent, 0);

						break;

					}
				}
			});

		} else {

			dt = new DietBDD(getActivity());
			l = new ArrayList<Diet>();

			dt.open();

			l = dt.selectAll();

			dt.close();
			if (l.isEmpty()) {
				if (l2.get(0).getIntensity().equals("easy")) {
					data = new CardItemData(R.drawable.diet, S2, l.get(0)
							.getDefaul() + " " + S3, l.get(0).getDefaul()
							.toString(), "5000");
					data2 = new CardItemData(R.drawable.steps2, S4, ""
							+ mStepValue + S5, String.valueOf(mStepValue),
							"450");
					data4 = new CardItemData(R.drawable.goalf, S6, "", "", "");
					data3 = new CardItemData(R.drawable.tofeh, S7, "", "", "");

				}
				if (l2.get(0).getIntensity().equals("average")) {
					data = new CardItemData(R.drawable.diet, S8, l.get(0)
							.getDefaul() + " " + S9, l.get(0).getDefaul()
							.toString(), "1750");
					data2 = new CardItemData(R.drawable.steps2, S10, ""
							+ mStepValue + S11, String.valueOf(mStepValue),
							"750");
					data4 = new CardItemData(R.drawable.goalf, S12, "", "", "");
					data3 = new CardItemData(R.drawable.tofeh, S13, "", "", "");

				}
				if (l2.get(0).getIntensity().equals("intense")) {
					data = new CardItemData(R.drawable.diet, S14, l.get(0)
							.getDefaul() + " " + S15, l.get(0).getDefaul()
							.toString(), "630");
					data2 = new CardItemData(R.drawable.steps2, S16, ""
							+ mStepValue + S17, String.valueOf(mStepValue),
							"1520");
					data4 = new CardItemData(R.drawable.goalf, S18, "", "", "");
					data3 = new CardItemData(R.drawable.tofeh, S19, "", "", "");

				}

			} else {

				if (l2.get(0).getIntensity().equals("easy")) {
					data = new CardItemData(R.drawable.diet, S2, l.get(0)
							.getMax() + " " + S3, l.get(0).getMax().toString(),
							"5000");
					data2 = new CardItemData(R.drawable.steps2, S4, ""
							+ mStepValue + S5, String.valueOf(mStepValue),
							"450");
					data4 = new CardItemData(R.drawable.goalf, S6, "", "", "");
					data3 = new CardItemData(R.drawable.tofeh, S7, "", "", "");

				}
				if (l2.get(0).getIntensity().equals("average")) {
					data = new CardItemData(R.drawable.diet, S3, l.get(0)
							.getMax() + " " + S9, l.get(0).getMax().toString(),
							"1750");
					data2 = new CardItemData(R.drawable.steps2, S10, ""
							+ mStepValue + S5, String.valueOf(mStepValue),
							"750");
					data4 = new CardItemData(R.drawable.goalf, S6, "", "", "");
					data3 = new CardItemData(R.drawable.tofeh, S7, "", "", "");

				}
				if (l2.get(0).getIntensity().equals("intense")) {
					data = new CardItemData(R.drawable.diet, S2, l.get(0)
							.getMax() + " " + S15,
							l.get(0).getMax().toString(), "630");
					data2 = new CardItemData(R.drawable.steps2, S16, ""
							+ mStepValue + S11, String.valueOf(mStepValue),
							"1520");
					data4 = new CardItemData(R.drawable.goalf, S6, "", "", "");

					data3 = new CardItemData(R.drawable.tofeh, S7, "", "", "");

				}
				// l.get(l.size() - 1).getLogged();

			}

			adapter3.addItem(data, false);
			adapter3.addItem(data2, false);
			adapter3.addItem(data3, false);
			adapter3.addItem(data4, false);

			mStepValueView = (TextView) root.findViewById(R.id.text3);
			list = (ListView) root.findViewById(R.id.list1);
			swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
					adapter3);
			swingBottomInAnimationAdapter.setAbsListView(list);

			list.setAdapter(swingBottomInAnimationAdapter);

			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					switch (position) {
					case 0:

						Intent intent1 = new Intent(getActivity(),
								LogDietsfirstActivity.class);

						startActivityForResult(intent1, 1);

						break;
					case 1:
						Intent intent2 = new Intent(getActivity(),
								StepsActivity.class);

						startActivity(intent2);
						break;
					case 2:
						Intent intent3 = new Intent(getActivity(),
								TipsActivity.class);

						startActivityForResult(intent3, 2);

						break;
					case 3:
						Intent intent = new Intent(getActivity(),
								GoalWeightActivity.class);

						startActivityForResult(intent, 0);

						break;

					}
				}
			});

		}
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
		// mStepValueView = (TextView) findViewById(R.id.stepsvalue);
		// mCaloriesValueView = (TextView) root.findViewById(R.id.textView226);

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

	static final int STEPS_MSG = 1;
	static final int PACE_MSG = 2;
	static final int DISTANCE_MSG = 3;
	static final int SPEED_MSG = 4;
	static final int CALORIES_MSG = 5;

	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STEPS_MSG:
				mStepValue = (int) msg.arg1;

				list.invalidateViews();
				adapter2.clear(true);
				adapter.clear(true);
				adapter3.clear(true);
				// pd = new ProgBDD(getActivity());
				// l2 = new ArrayList<Program>();
				// pd.open();
				// l2 = pd.selectAll();
				// pd.close();

				if (l2.isEmpty()) {

					// Diet d = new Diet("0", "0");
					// DietBDD dtBDD1 = new DietBDD(getActivity());
					// dtBDD1.open();
					// dtBDD1.insertTop(d);
					// dtBDD1.close();

					data4 = new CardItemData(R.drawable.goal, S1, "", "", "");

					adapter.addItem(data4, false);

					mStepValueView = (TextView) root.findViewById(R.id.text3);
					list = (ListView) root.findViewById(R.id.list1);
					swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
							adapter);
					swingBottomInAnimationAdapter.setAbsListView(list);

					list.setAdapter(swingBottomInAnimationAdapter);
					// setBottomAdapter();
					list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							switch (position) {
							case 0:

								// setBottomAdapter();

								// list.setAdapter(swingBottomInAnimationAdapter);
								Intent intent = new Intent(getActivity(),
										GoalWeightActivity.class);

								startActivityForResult(intent, 0);

								break;

							}
						}
					});

				} else {

					// dt = new DietBDD(getActivity());
					// l = new ArrayList<Diet>();
					//
					// dt.open();
					//
					// l = dt.selectAll();
					//
					// dt.close();
					if (l.isEmpty()) {
						if (l2.get(0).getIntensity().equals("easy")) {
							data = new CardItemData(R.drawable.diet, S2, l.get(
									0).getDefaul()
									+ " " + S3,
									l.get(0).getDefaul().toString(), "5000");
							data2 = new CardItemData(R.drawable.steps2, S4, ""
									+ mStepValue + S5,
									String.valueOf(mStepValue), "450");
							data4 = new CardItemData(R.drawable.goalf, S6, "",
									"", "");
							data3 = new CardItemData(R.drawable.tofeh, S7, "",
									"", "");

						}
						if (l2.get(0).getIntensity().equals("average")) {
							data = new CardItemData(R.drawable.diet, S8, l.get(
									0).getDefaul()
									+ " " + S9,
									l.get(0).getDefaul().toString(), "1750");
							data2 = new CardItemData(R.drawable.steps2, S10, ""
									+ mStepValue + S11,
									String.valueOf(mStepValue), "750");
							data4 = new CardItemData(R.drawable.goalf, S12, "",
									"", "");
							data3 = new CardItemData(R.drawable.tofeh, S13, "",
									"", "");

						}
						if (l2.get(0).getIntensity().equals("intense")) {
							data = new CardItemData(R.drawable.diet, S14, l
									.get(0).getDefaul() + " " + S15, l.get(0)
									.getDefaul().toString(), "630");
							data2 = new CardItemData(R.drawable.steps2, S16, ""
									+ mStepValue + S17,
									String.valueOf(mStepValue), "1520");
							data4 = new CardItemData(R.drawable.goalf, S18, "",
									"", "");
							data3 = new CardItemData(R.drawable.tofeh, S19, "",
									"", "");

						}

					} else {

						if (l2.get(0).getIntensity().equals("easy")) {
							data = new CardItemData(R.drawable.diet, S2, l.get(
									0).getMax()
									+ " " + S3, l.get(0).getMax().toString(),
									"5000");
							data2 = new CardItemData(R.drawable.steps2, S4, ""
									+ mStepValue + S5,
									String.valueOf(mStepValue), "450");
							data4 = new CardItemData(R.drawable.goalf, S6, "",
									"", "");
							data3 = new CardItemData(R.drawable.tofeh, S7, "",
									"", "");

						}
						if (l2.get(0).getIntensity().equals("average")) {
							data = new CardItemData(R.drawable.diet, S3, l.get(
									0).getMax()
									+ " " + S9, l.get(0).getMax().toString(),
									"1750");
							data2 = new CardItemData(R.drawable.steps2, S10, ""
									+ mStepValue + S5,
									String.valueOf(mStepValue), "750");
							data4 = new CardItemData(R.drawable.goalf, S6, "",
									"", "");
							data3 = new CardItemData(R.drawable.tofeh, S7, "",
									"", "");

						}
						if (l2.get(0).getIntensity().equals("intense")) {
							data = new CardItemData(R.drawable.diet, S2, l.get(
									0).getMax()
									+ " " + S15, l.get(0).getMax().toString(),
									"630");
							data2 = new CardItemData(R.drawable.steps2, S16, ""
									+ mStepValue + S11,
									String.valueOf(mStepValue), "1520");
							data4 = new CardItemData(R.drawable.goalf, S6, "",
									"", "");

							data3 = new CardItemData(R.drawable.tofeh, S7, "",
									"", "");

						}
						// l.get(l.size() - 1).getLogged();

					}

					adapter3.addItem(data, false);
					adapter3.addItem(data2, false);
					adapter3.addItem(data3, false);
					adapter3.addItem(data4, false);

					mStepValueView = (TextView) root.findViewById(R.id.text3);
					list = (ListView) root.findViewById(R.id.list1);

					list.setAdapter(adapter3);
					adapter3.notifyDataSetChanged();
					list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							switch (position) {
							case 0:

								Intent intent1 = new Intent(getActivity(),
										LogDietsfirstActivity.class);

								startActivityForResult(intent1, 1);

								break;
							case 1:
								Intent intent2 = new Intent(getActivity(),
										StepsActivity.class);

								startActivity(intent2);
								break;
							case 2:
								Intent intent3 = new Intent(getActivity(),
										TipsActivity.class);

								startActivityForResult(intent3, 2);

								break;
							case 3:
								Intent intent = new Intent(getActivity(),
										GoalWeightActivity.class);

								startActivityForResult(intent, 0);

								break;

							}
						}
					});
				}
				break;

			// case CALORIES_MSG:
			// mCaloriesValue = msg.arg1;
			// if (mCaloriesValue <= 0) {
			// mCaloriesValueView.setText("0");
			// } else {
			// mCaloriesValueView.setText("" + (int) mCaloriesValue);
			// }
			// break;
			default:
				super.handleMessage(msg);
			}
		}

	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent datad) {

		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			list.invalidateViews();
			adapter2.clear(true);
			adapter.clear(true);
			adapter3.clear(true);
			pd = new ProgBDD(getActivity());
			l2 = new ArrayList<Program>();
			pd.open();
			l2 = pd.selectAll();
			pd.close();

			if (l2.isEmpty()) {

				Diet d = new Diet("0", "0");
				DietBDD dtBDD1 = new DietBDD(getActivity());
				dtBDD1.open();
				dtBDD1.insertTop(d);
				dtBDD1.close();

				data4 = new CardItemData(R.drawable.goal, S1, "", "", "");

				adapter.addItem(data4, false);

				mStepValueView = (TextView) root.findViewById(R.id.text3);
				list = (ListView) root.findViewById(R.id.list1);
				swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
						adapter);
				swingBottomInAnimationAdapter.setAbsListView(list);

				list.setAdapter(swingBottomInAnimationAdapter);
				// setBottomAdapter();
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						switch (position) {
						case 0:

							// setBottomAdapter();

							// list.setAdapter(swingBottomInAnimationAdapter);
							Intent intent = new Intent(getActivity(),
									GoalWeightActivity.class);

							startActivityForResult(intent, 0);

							break;

						}
					}
				});

			} else {

				dt = new DietBDD(getActivity());
				l = new ArrayList<Diet>();

				dt.open();

				l = dt.selectAll();

				dt.close();
				if (l.isEmpty()) {
					if (l2.get(0).getIntensity().equals("easy")) {
						data = new CardItemData(R.drawable.diet, S2, l.get(0)
								.getDefaul() + " " + S3, l.get(0).getDefaul()
								.toString(), "5000");
						data2 = new CardItemData(R.drawable.steps2, S4, ""
								+ mStepValue + S5, String.valueOf(mStepValue),
								"450");
						data4 = new CardItemData(R.drawable.goalf, S6, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S7, "", "",
								"");

					}
					if (l2.get(0).getIntensity().equals("average")) {
						data = new CardItemData(R.drawable.diet, S8, l.get(0)
								.getDefaul() + " " + S9, l.get(0).getDefaul()
								.toString(), "1750");
						data2 = new CardItemData(R.drawable.steps2, S10, ""
								+ mStepValue + S11, String.valueOf(mStepValue),
								"750");
						data4 = new CardItemData(R.drawable.goalf, S12, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S13, "", "",
								"");

					}
					if (l2.get(0).getIntensity().equals("intense")) {
						data = new CardItemData(R.drawable.diet, S14, l.get(0)
								.getDefaul() + " " + S15, l.get(0).getDefaul()
								.toString(), "630");
						data2 = new CardItemData(R.drawable.steps2, S16, ""
								+ mStepValue + S17, String.valueOf(mStepValue),
								"1520");
						data4 = new CardItemData(R.drawable.goalf, S18, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S19, "", "",
								"");

					}

				} else {

					if (l2.get(0).getIntensity().equals("easy")) {
						data = new CardItemData(R.drawable.diet, S2, l.get(0)
								.getMax() + " " + S3, l.get(0).getMax()
								.toString(), "5000");
						data2 = new CardItemData(R.drawable.steps2, S4, ""
								+ mStepValue + S5, String.valueOf(mStepValue),
								"450");
						data4 = new CardItemData(R.drawable.goalf, S6, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S7, "", "",
								"");

					}
					if (l2.get(0).getIntensity().equals("average")) {
						data = new CardItemData(R.drawable.diet, S3, l.get(0)
								.getMax() + " " + S9, l.get(0).getMax()
								.toString(), "1750");
						data2 = new CardItemData(R.drawable.steps2, S10, ""
								+ mStepValue + S5, String.valueOf(mStepValue),
								"750");
						data4 = new CardItemData(R.drawable.goalf, S6, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S7, "", "",
								"");

					}
					if (l2.get(0).getIntensity().equals("intense")) {
						data = new CardItemData(R.drawable.diet, S2, l.get(0)
								.getMax() + " " + S15, l.get(0).getMax()
								.toString(), "630");
						data2 = new CardItemData(R.drawable.steps2, S16, ""
								+ mStepValue + S11, String.valueOf(mStepValue),
								"1520");
						data4 = new CardItemData(R.drawable.goalf, S6, "", "",
								"");

						data3 = new CardItemData(R.drawable.tofeh, S7, "", "",
								"");

					}
					// l.get(l.size() - 1).getLogged();

				}

				adapter3.addItem(data, false);
				adapter3.addItem(data2, false);
				adapter3.addItem(data3, false);
				adapter3.addItem(data4, false);

				mStepValueView = (TextView) root.findViewById(R.id.text3);
				list = (ListView) root.findViewById(R.id.list1);
				swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
						adapter3);
				swingBottomInAnimationAdapter.setAbsListView(list);

				list.setAdapter(swingBottomInAnimationAdapter);
				adapter3.notifyDataSetChanged();
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						switch (position) {
						case 0:

							Intent intent1 = new Intent(getActivity(),
									LogDietsfirstActivity.class);

							startActivityForResult(intent1, 1);

							break;
						case 1:
							Intent intent2 = new Intent(getActivity(),
									StepsActivity.class);

							startActivity(intent2);
							break;
						case 2:
							Intent intent3 = new Intent(getActivity(),
									TipsActivity.class);

							startActivityForResult(intent3, 2);

							break;
						case 3:
							Intent intent = new Intent(getActivity(),
									GoalWeightActivity.class);

							startActivityForResult(intent, 0);

							break;

						}
					}
				});

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						showCustomViewCrouton();
					}
				}, 1000);

			}

		}
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			list.invalidateViews();

			adapter.clear(true);
			adapter3.clear(true);
			pd = new ProgBDD(getActivity());
			l2 = new ArrayList<Program>();
			pd.open();
			l2 = pd.selectAll();
			pd.close();

			if (l2.isEmpty()) {

				Diet d = new Diet("0", "0");
				DietBDD dtBDD1 = new DietBDD(getActivity());
				dtBDD1.open();
				dtBDD1.insertTop(d);
				dtBDD1.close();

				data4 = new CardItemData(R.drawable.goal, S1, "", "", "");

				adapter.addItem(data4, false);

				mStepValueView = (TextView) root.findViewById(R.id.text3);
				list = (ListView) root.findViewById(R.id.list1);
				swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
						adapter);
				swingBottomInAnimationAdapter.setAbsListView(list);

				list.setAdapter(swingBottomInAnimationAdapter);
				// setBottomAdapter();
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						switch (position) {
						case 0:

							// setBottomAdapter();

							// list.setAdapter(swingBottomInAnimationAdapter);
							Intent intent = new Intent(getActivity(),
									GoalWeightActivity.class);

							startActivityForResult(intent, 0);

							break;

						}
					}
				});

			} else {

				dt = new DietBDD(getActivity());
				l = new ArrayList<Diet>();

				dt.open();

				l = dt.selectAll();

				dt.close();
				if (l.isEmpty()) {
					if (l2.get(0).getIntensity().equals("easy")) {
						data = new CardItemData(R.drawable.diet, S2, l.get(0)
								.getDefaul() + " " + S3, l.get(0).getDefaul()
								.toString(), "5000");
						data2 = new CardItemData(R.drawable.steps2, S4, ""
								+ mStepValue + S5, String.valueOf(mStepValue),
								"450");
						data4 = new CardItemData(R.drawable.goalf, S6, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S7, "", "",
								"");

					}
					if (l2.get(0).getIntensity().equals("average")) {
						data = new CardItemData(R.drawable.diet, S8, l.get(0)
								.getDefaul() + " " + S9, l.get(0).getDefaul()
								.toString(), "1750");
						data2 = new CardItemData(R.drawable.steps2, S10, ""
								+ mStepValue + S11, String.valueOf(mStepValue),
								"750");
						data4 = new CardItemData(R.drawable.goalf, S12, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S13, "", "",
								"");

					}
					if (l2.get(0).getIntensity().equals("intense")) {
						data = new CardItemData(R.drawable.diet, S14, l.get(0)
								.getDefaul() + " " + S15, l.get(0).getDefaul()
								.toString(), "630");
						data2 = new CardItemData(R.drawable.steps2, S16, ""
								+ mStepValue + S17, String.valueOf(mStepValue),
								"1520");
						data4 = new CardItemData(R.drawable.goalf, S18, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S19, "", "",
								"");

					}

				} else {

					if (l2.get(0).getIntensity().equals("easy")) {
						data = new CardItemData(R.drawable.diet, S2, l.get(0)
								.getMax() + " " + S3, l.get(0).getMax()
								.toString(), "5000");
						data2 = new CardItemData(R.drawable.steps2, S4, ""
								+ mStepValue + S5, String.valueOf(mStepValue),
								"450");
						data4 = new CardItemData(R.drawable.goalf, S6, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S7, "", "",
								"");

					}
					if (l2.get(0).getIntensity().equals("average")) {
						data = new CardItemData(R.drawable.diet, S3, l.get(0)
								.getMax() + " " + S9, l.get(0).getMax()
								.toString(), "1750");
						data2 = new CardItemData(R.drawable.steps2, S10, ""
								+ mStepValue + S5, String.valueOf(mStepValue),
								"750");
						data4 = new CardItemData(R.drawable.goalf, S6, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S7, "", "",
								"");

					}
					if (l2.get(0).getIntensity().equals("intense")) {
						data = new CardItemData(R.drawable.diet, S2, l.get(0)
								.getMax() + " " + S15, l.get(0).getMax()
								.toString(), "630");
						data2 = new CardItemData(R.drawable.steps2, S16, ""
								+ mStepValue + S11, String.valueOf(mStepValue),
								"1520");
						data4 = new CardItemData(R.drawable.goalf, S6, "", "",
								"");

						data3 = new CardItemData(R.drawable.tofeh, S7, "", "",
								"");

					}
					// l.get(l.size() - 1).getLogged();

				}

				adapter3.addItem(data, false);
				adapter3.addItem(data2, false);
				adapter3.addItem(data3, false);
				adapter3.addItem(data4, false);
				adapter3.notifyDataSetChanged();
				mStepValueView = (TextView) root.findViewById(R.id.text3);
				list = (ListView) root.findViewById(R.id.list1);
				swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
						adapter3);
				swingBottomInAnimationAdapter.setAbsListView(list);

				list.setAdapter(swingBottomInAnimationAdapter);

				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						switch (position) {
						case 0:

							Intent intent1 = new Intent(getActivity(),
									LogDietsfirstActivity.class);

							startActivityForResult(intent1, 1);

							break;
						case 1:
							Intent intent2 = new Intent(getActivity(),
									StepsActivity.class);

							startActivity(intent2);
							break;
						case 3:
							Intent intent = new Intent(getActivity(),
									GoalWeightActivity.class);

							startActivityForResult(intent, 0);

							break;

						}
					}
				});

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						showCustomViewCrouton1();
					}
				}, 1000);

			}

		}
		if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
			list.invalidateViews();

			adapter.clear(true);
			adapter3.clear(true);
			pd = new ProgBDD(getActivity());
			l2 = new ArrayList<Program>();
			pd.open();
			l2 = pd.selectAll();
			pd.close();

			if (l2.isEmpty()) {

				Diet d = new Diet("0", "0");
				DietBDD dtBDD1 = new DietBDD(getActivity());
				dtBDD1.open();
				dtBDD1.insertTop(d);
				dtBDD1.close();

				data4 = new CardItemData(R.drawable.goal, S1, "", "", "");

				adapter.addItem(data4, false);

				mStepValueView = (TextView) root.findViewById(R.id.text3);
				list = (ListView) root.findViewById(R.id.list1);
				swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
						adapter);
				swingBottomInAnimationAdapter.setAbsListView(list);

				list.setAdapter(swingBottomInAnimationAdapter);
				// setBottomAdapter();
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						switch (position) {
						case 0:

							// setBottomAdapter();

							// list.setAdapter(swingBottomInAnimationAdapter);
							Intent intent = new Intent(getActivity(),
									GoalWeightActivity.class);

							startActivityForResult(intent, 0);

							break;

						}
					}
				});

			} else {

				dt = new DietBDD(getActivity());
				l = new ArrayList<Diet>();

				dt.open();

				l = dt.selectAll();

				dt.close();
				if (l.isEmpty()) {
					if (l2.get(0).getIntensity().equals("easy")) {
						data = new CardItemData(R.drawable.diet, S2, l.get(0)
								.getDefaul() + " " + S3, l.get(0).getDefaul()
								.toString(), "5000");
						data2 = new CardItemData(R.drawable.steps2, S4, ""
								+ mStepValue + S5, String.valueOf(mStepValue),
								"450");
						data4 = new CardItemData(R.drawable.goalf, S6, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S7, "", "",
								"");

					}
					if (l2.get(0).getIntensity().equals("average")) {
						data = new CardItemData(R.drawable.diet, S8, l.get(0)
								.getDefaul() + " " + S9, l.get(0).getDefaul()
								.toString(), "1750");
						data2 = new CardItemData(R.drawable.steps2, S10, ""
								+ mStepValue + S11, String.valueOf(mStepValue),
								"750");
						data4 = new CardItemData(R.drawable.goalf, S12, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S13, "", "",
								"");

					}
					if (l2.get(0).getIntensity().equals("intense")) {
						data = new CardItemData(R.drawable.diet, S14, l.get(0)
								.getDefaul() + " " + S15, l.get(0).getDefaul()
								.toString(), "630");
						data2 = new CardItemData(R.drawable.steps2, S16, ""
								+ mStepValue + S17, String.valueOf(mStepValue),
								"1520");
						data4 = new CardItemData(R.drawable.goalf, S18, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S19, "", "",
								"");

					}

				} else {

					if (l2.get(0).getIntensity().equals("easy")) {
						data = new CardItemData(R.drawable.diet, S2, l.get(0)
								.getMax() + " " + S3, l.get(0).getMax()
								.toString(), "5000");
						data2 = new CardItemData(R.drawable.steps2, S4, ""
								+ mStepValue + S5, String.valueOf(mStepValue),
								"450");
						data4 = new CardItemData(R.drawable.goalf, S6, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S7, "", "",
								"");

					}
					if (l2.get(0).getIntensity().equals("average")) {
						data = new CardItemData(R.drawable.diet, S3, l.get(0)
								.getMax() + " " + S9, l.get(0).getMax()
								.toString(), "1750");
						data2 = new CardItemData(R.drawable.steps2, S10, ""
								+ mStepValue + S5, String.valueOf(mStepValue),
								"750");
						data4 = new CardItemData(R.drawable.goalf, S6, "", "",
								"");
						data3 = new CardItemData(R.drawable.tofeh, S7, "", "",
								"");

					}
					if (l2.get(0).getIntensity().equals("intense")) {
						data = new CardItemData(R.drawable.diet, S2, l.get(0)
								.getMax() + " " + S15, l.get(0).getMax()
								.toString(), "630");
						data2 = new CardItemData(R.drawable.steps2, S16, ""
								+ mStepValue + S11, String.valueOf(mStepValue),
								"1520");
						data4 = new CardItemData(R.drawable.goalf, S6, "", "",
								"");

						data3 = new CardItemData(R.drawable.tofeh, S7, "", "",
								"");

					}
					// l.get(l.size() - 1).getLogged();

				}

				adapter3.addItem(data, false);
				adapter3.addItem(data2, false);
				adapter3.addItem(data3, false);
				adapter3.addItem(data4, false);
				adapter3.notifyDataSetChanged();
				mStepValueView = (TextView) root.findViewById(R.id.text3);
				list = (ListView) root.findViewById(R.id.list1);
				swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
						adapter3);
				swingBottomInAnimationAdapter.setAbsListView(list);

				list.setAdapter(swingBottomInAnimationAdapter);

				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						switch (position) {
						case 0:

							Intent intent1 = new Intent(getActivity(),
									LogDietsfirstActivity.class);

							startActivityForResult(intent1, 1);

							break;
						case 1:
							Intent intent2 = new Intent(getActivity(),
									StepsActivity.class);

							startActivity(intent2);
							break;
						case 3:
							Intent intent = new Intent(getActivity(),
									GoalWeightActivity.class);

							startActivityForResult(intent, 0);

							break;

						}
					}
				});

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						showCustomViewCrouton2();
					}
				}, 1000);

			}

		}

	};

	@SuppressLint("InflateParams")
	private void showCustomViewCrouton() {

		LayoutInflater mInflater;

		Context context = getActivity().getApplicationContext();
		mInflater = LayoutInflater.from(context);
		View view = mInflater.inflate(R.layout.crouton_custom_view, null);
		final Crouton crouton;
		ImageView image;
		TextView title;
		TextView message;
		image = (ImageView) view.findViewById(R.id.iconpop);
		title = (TextView) view.findViewById(R.id.titlepop);
		message = (TextView) view.findViewById(R.id.messpop);

		image.setImageResource(R.drawable.goals);
		title.setText(getString(R.string.cali12));
		message.setText("+ 25 Cali");
		crouton = Crouton.make(getActivity(), view, R.id.alternate_view_group);

		crouton.show();
	}

	@SuppressLint("InflateParams")
	private void showCustomViewCrouton1() {

		LayoutInflater mInflater;

		Context context = getActivity().getApplicationContext();
		mInflater = LayoutInflater.from(context);
		View view = mInflater.inflate(R.layout.crouton_custom_view, null);
		final Crouton crouton;
		ImageView image;
		TextView title;
		TextView message;
		image = (ImageView) view.findViewById(R.id.iconpop);
		title = (TextView) view.findViewById(R.id.titlepop);
		message = (TextView) view.findViewById(R.id.messpop);

		image.setImageResource(R.drawable.diets);
		title.setText(getString(R.string.cali13));
		message.setText("+ 50 Cali");
		crouton = Crouton.make(getActivity(), view, R.id.alternate_view_group);

		crouton.show();
	}

	@SuppressLint("InflateParams")
	private void showCustomViewCrouton2() {

		LayoutInflater mInflater;

		Context context = getActivity().getApplicationContext();
		mInflater = LayoutInflater.from(context);
		View view = mInflater.inflate(R.layout.crouton_custom_view, null);
		final Crouton crouton;
		ImageView image;
		TextView title;
		TextView message;
		image = (ImageView) view.findViewById(R.id.iconpop);
		title = (TextView) view.findViewById(R.id.titlepop);
		message = (TextView) view.findViewById(R.id.messpop);

		image.setImageResource(R.drawable.tofehs);
		title.setText(getString(R.string.cali14));
		message.setText("+ 200 Cali");
		crouton = Crouton.make(getActivity(), view, R.id.alternate_view_group);

		crouton.show();
	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getActivity().getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

}
