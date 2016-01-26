package com.andy.cali.activity;



import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.andy.cali.R;
import com.andy.entities.Details;
import com.andy.entities.Nutrition;
import com.andy.entities.Tips;
import com.andy.entities.User;
import com.andy.sqlite.DetailsBDD;
import com.andy.sqlite.NutritionBDD;
import com.andy.sqlite.TipsBDD;
import com.andy.sqlite.UserBDD;
import com.andy.utils.ConnectionDetector;
import com.facebook.AppEventsLogger;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.widgets.Dialog;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pnikosis.materialishprogress.ProgressWheel;

public class SplashScreenActivity extends Activity {
	List<ParseObject> ob;
	List<ParseObject> ob2;
	List<ParseObject> ob3;
	String id_N;
	String name_N;
	String category_N;
	String id_NC;
	String part1;
	String part2;
	String part3;
	String part4;
	String val1;
	String val2;
	String val3;
	String val4;
	ImageView logo;
	Animation anim1;
	Animation anim2;
	Animation anim3;
	Boolean isInternetPresent;
	ConnectionDetector cd;
	ScaleAnimation makeBigger;
	// ImageView start;
	ButtonFloat start1;
	RelativeLayout relativeLayout2;
	RelativeLayout relativeLayout;
	UserBDD userBDD;
	List<User> l;
	NutritionBDD nBDD;
	DetailsBDD dBDD;
 ProgressWheel progressWheel ;
	String title1 ;
	String title2;
	String title3;
	String desc1;
	String desc2;
	String desc3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();

		nBDD = new NutritionBDD(getApplicationContext());
		nBDD.open();
		nBDD.removeAllArticles();

		nBDD.close();
		dBDD = new DetailsBDD(getApplicationContext());
		dBDD.open();
		dBDD.removeAllArticles();

		dBDD.close();

		getActionBar().hide();
		setContentView(R.layout.activity_splash);

	progressWheel = (ProgressWheel) findViewById(R.id.progressBar1);

		relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout1);
		// relativeLayout2 = (RelativeLayout) findViewById(R.id.flat);
		relativeLayout.setClipChildren(false);
		logo = (ImageView) findViewById(R.id.imgLogo);

		// start = (ImageView) findViewById(R.id.imageView1);

		start1 = (ButtonFloat) findViewById(R.id.buttonFloat);

		anim1 = AnimationUtils.loadAnimation(this, R.anim.anim1);
		anim1.reset();
		anim2 = AnimationUtils.loadAnimation(this, R.anim.anim2);
		anim2.reset();
		anim3 = AnimationUtils.loadAnimation(this, R.anim.anim3);
		anim3.reset();
		logo.clearAnimation();
		logo.startAnimation(anim2);
		start1.setVisibility(View.INVISIBLE);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				makeBigger = new ScaleAnimation((float) 1.0, (float) 1.5,
						(float) 1.0, (float) 1.5, Animation.RELATIVE_TO_SELF,
						(float) 0.5, Animation.RELATIVE_TO_SELF, (float) 3.2);

				makeBigger.setFillAfter(true);

				makeBigger.setDuration(750);

				logo.startAnimation(makeBigger);
				if (isInternetPresent == true) {
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							ProgressWheel wheel = new ProgressWheel(getApplicationContext());
							wheel.setBarColor(Color.parseColor("#1E88E5"));
							progressWheel.setVisibility(View.VISIBLE);
						
							new RemoteDataTask().execute();

						}
					}, 1600);
				} else {
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {

							final Dialog dialog = new Dialog(
									SplashScreenActivity.this,
									getResources().getString(R.string.activate), "");
							dialog.setOnAcceptButtonClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog.dismiss();
									startActivityForResult(
											new Intent(
													android.provider.Settings.ACTION_WIFI_SETTINGS),
											0);

								}
							});
							dialog.setOnCancelButtonClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									finish();
								}
							});
							dialog.show();
						}
					}, 1600);

				}
			}
		}, 1500);

	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	public void Startact() {
		// TODO Auto-generated method stub

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				progressWheel.setVisibility(View.INVISIBLE);
				start1.refreshDrawableState();
				start1.setVisibility(View.VISIBLE);
				start1.clearAnimation();

				start1.startAnimation(anim3);

				start1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						Intent intent = new Intent(getApplicationContext(),
								ConnectActivity.class);
						startActivity(intent);
//						overridePendingTransition(R.anim.slide_in_from_bottom,
//								R.anim.slide_out_to_bottom);

						finish();

					}
				});
			}
		}, 2500);

		super.onStart();
	}

	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// Locate the class table named "Country" in Parse.com
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"Nutrition");
			ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>(
					"Details");
			ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Tips");
			query.orderByDescending("Name");

			try {
				try {
				
					ob3 = query2.find();
					ob = query.find();
					ob2 = query1.find();
				} catch (com.parse.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ParseException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// Locate the listview in listview_main.xml

			for (ParseObject country : ob) {
				id_N = (String) country.get("Id_N");
				name_N = (String) country.get("Name");
				category_N = (String) country.get("Category");
				Nutrition nt = new Nutrition(id_N, name_N, category_N);
				nBDD = new NutritionBDD(getApplicationContext());
				nBDD.open();
				// nBDD.removeAllArticles();
				nBDD.insertTop(nt);
				nBDD.close();

			}
			for (ParseObject country : ob3) {
				title1 = (String) country.get("Title1");
				title2 = (String) country.get("Title2");
				title3 = (String) country.get("Title3");
				desc1 = (String) country.get("Desc1");
				desc2 = (String) country.get("Desc2");
				desc3 = (String) country.get("Desc3");
				ParseFile image = country.getParseFile("Photot");
				image.getDataInBackground(new GetDataCallback() {

					@Override
					public void done(byte[] arg0, com.parse.ParseException arg1) {
						// TODO Auto-generated method stub
						// Bitmap bi = BitmapFactory.decodeByteArray(arg0, 0,
						// arg0.length);

						Tips nt = new Tips(title1, desc1, title2, desc2,title3,desc3, arg0);
						TipsBDD tBDD = new TipsBDD(getApplicationContext());
						tBDD.open();
						tBDD.removeAllArticles();
						tBDD.insertTop(nt);
						tBDD.close();

					}
				});

			}
		

			for (ParseObject country : ob2) {
				id_NC = (String) country.get("Id_DN");
				part1 = (String) country.get("Part1");
				part2 = (String) country.get("Part2");
				part3 = (String) country.get("Part3");
				part4 = (String) country.get("Part4");
				val1 = (String) country.get("Val1");
				val2 = (String) country.get("Val2");
				val3 = (String) country.get("Val3");
				val4 = (String) country.get("Val4");
				Details dt = new Details(id_NC, part1, val1, part2, val2,
						part3, val3, part4, val4);

				dBDD = new DetailsBDD(getApplicationContext());
				dBDD.open();
				// dBDD.removeAllArticles();
				dBDD.insertTop(dt);
				dBDD.close();

			}

			l = new ArrayList<User>();
			userBDD = new UserBDD(getApplicationContext());
			userBDD.open();

			l = userBDD.selectAll();

			userBDD.close();
			if (l.isEmpty()) {
				Startact();
			} else {

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {

						Intent intent = new Intent(SplashScreenActivity.this,
								ContainerActivity.class);

						startActivity(intent);

						finish();
					}
				}, 2500);
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent == true) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					ProgressWheel wheel = new ProgressWheel(getApplicationContext());
					wheel.setBarColor(Color.parseColor("#1E88E5"));
					progressWheel.setVisibility(View.VISIBLE);
				
					new RemoteDataTask().execute();

				}
			}, 1600);
		} else {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					final Dialog dialog = new Dialog(SplashScreenActivity.this,
							"Activate Internet Connexion", "");
					dialog.setOnAcceptButtonClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							startActivityForResult(
									new Intent(
											android.provider.Settings.ACTION_WIFI_SETTINGS),
									0);
							dialog.dismiss();
						}
					});
					dialog.setOnCancelButtonClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
							finish();
						}
					});
					dialog.show();
				}
			}, 1100);

		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	@Override
	protected void onResume() {
	  super.onResume();

	  // Logs 'install' and 'app activate' App Events.
	  AppEventsLogger.activateApp(this);
	}
	@Override
	protected void onPause() {
	  super.onPause();

	  // Logs 'app deactivate' App Event.
	  AppEventsLogger.deactivateApp(this);
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
