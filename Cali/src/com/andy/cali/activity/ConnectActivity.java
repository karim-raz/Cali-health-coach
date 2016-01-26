package com.andy.cali.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.andy.cali.R;
import com.andy.fragment.FirstUsePageFragment;
import com.andy.fragment.FirstUsePageMiniFragment;
import com.andy.utils.CirclePageIndicator;
import com.andy.utils.MomentUtil;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class ConnectActivity extends Activity implements OnClickListener,
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener, ConnectionCallbacks {
	public ViewPager mPager;
	public ViewPager mPagerMini;
	String id;
	String namee;
	String email;
	List<String> permissions;
	public PagerAdapter mPagerAdapter;
	public PagerAdapter mPagerAdapterMini;
	public CirclePageIndicator circlePageIndicator;
	LoginButton login;
	private static final int DIALOG_GET_GOOGLE_PLAY_SERVICES = 1;

	private static final int REQUEST_CODE_SIGN_IN = 1;
	private static final int REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES = 2;

	private ProgressDialog mConnectionProgressDialog;
	private GoogleApiClient mPlusClient;
	private ConnectionResult mConnectionResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mPlusClient = new GoogleApiClient.Builder(getApplicationContext())
				.addApi(Plus.API,
						Plus.PlusOptions.builder()
								.addActivityTypes(MomentUtil.ACTIONS).build())
				.addScope(Plus.SCOPE_PLUS_LOGIN).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).build();
		// Barre de progression à afficher si l'échec de connexion n'est pas
		// résolu.
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Signing in...");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		permissions = new ArrayList<String>();
		//
		// SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// tintManager.setStatusBarTintEnabled(true);
		// tintManager.setStatusBarTintResource(R.color.statusbar_bg2);
		setContentView(R.layout.connect_layout);
		getActionBar().hide();
		login = (LoginButton) findViewById(R.id.login_button1);
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerMini = (ViewPager) findViewById(R.id.pager2);
		findViewById(R.id.sign_in_button).setOnClickListener(this);
		mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
		mPagerAdapterMini = new ScreenSlidePagerMiniAdapter(
				getFragmentManager());

		mPager.setAdapter(mPagerAdapter);
		mPagerMini.setAdapter(mPagerAdapterMini);
		circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicateur1);

		circlePageIndicator.setViewPager(mPager);
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {

				invalidateOptionsMenu();

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				mScrollState = arg0;
				if (mScrollState == ViewPager.SCROLL_STATE_IDLE)

					mPagerMini.setCurrentItem(mPager.getCurrentItem(), false);

			}

			int mScrollState = ViewPager.SCROLL_STATE_IDLE;

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if (mScrollState == ViewPager.SCROLL_STATE_IDLE)
					return;
				mPagerMini.scrollTo((int) (mPager.getScrollX() * 0.54),
						(int) (mPager.getScrollY()));
			}
		});

		mPagerMini
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						invalidateOptionsMenu();
						circlePageIndicator.setCurrentItem(position);
					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
						mScrollState = arg0;
						if (mScrollState == ViewPager.SCROLL_STATE_IDLE)

							mPager.setCurrentItem(mPagerMini.getCurrentItem(),
									false);
					}

					int mScrollState = ViewPager.SCROLL_STATE_IDLE;

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						if (mScrollState == ViewPager.SCROLL_STATE_IDLE)
							return;
						mPager.scrollTo((int) (mPagerMini.getScrollX() * 1.82),
								(int) (mPagerMini.getScrollY()));
					}
				});
		

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// start Facebook Login
				// Bundle params = me.getParameters();
				// params.putString("fields", "email,name");
				// me.setParameters(params);
				// me.executeAsync();

				permissions.add("email");
				openActiveSession(ConnectActivity.this, true,
						new Session.StatusCallback() {

							// callback when session changes state
							@Override
							public void call(Session session,
									SessionState state, Exception exception) {
								if (session.isOpened()) {
									Log.e("sessionopened", "true");
									// make request to the /me API
									Request.newMeRequest(session,
											new Request.GraphUserCallback() {

												// callback after Graph API
												// response with user
												// object

												@Override
												public void onCompleted(
														GraphUser user,
														Response response) {
													if (user != null) {

														// TextView welcome
														// =
														// (TextView)
														// findViewById(R.id.welcome);
														// welcome.setText("Hello "
														// +
														// user.getName() +
														// "!");
														id = user.getId()
																.toString();
														namee = user.getName()
																.toString();
														email = user
																.getProperty(
																		"email")
																.toString();

														Intent intent = new Intent(
																ConnectActivity.this,
																FormActivity.class);

														intent.putExtra("id",
																id);
														intent.putExtra("name",
																namee);
														intent.putExtra("from",
																"facebook");
														intent.putExtra(
																"email", email);
														startActivity(intent);
														// overridePendingTransition(
														// R.anim.slide_in_from_bottom,
														// R.anim.slide_out_to_bottom);
														finish();

													}
												}
											}).executeAsync();
									// Bundle params = me.getParameters();
									// params.putString("fields", "email,name");
									// me.setParameters(params);
									// me.executeAsync();

								}
							}
						}, permissions);
			}

		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_SIGN_IN
				|| requestCode == REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES) {
			if (resultCode == RESULT_CANCELED) {
				// mSignInStatus.setText(getString(R.string.signed_out_status));
			} else if (resultCode == RESULT_OK && !mPlusClient.isConnected()
					&& !mPlusClient.isConnecting()) {
				// This time, connect should succeed.
				mPlusClient.connect();
			}
		} else {

			Session.getActiveSession().onActivityResult(this, requestCode,
					resultCode, data);
		}
	}

	public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return FirstUsePageFragment.create(position);
		}

		@Override
		public int getCount() {
			return 4;
		}
	}

	public class ScreenSlidePagerMiniAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerMiniAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return FirstUsePageMiniFragment.create(position);
		}

		@Override
		public int getCount() {
			return 4;
		}
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// if (v.getId() == R.id.sign_in_button && !mPlusClient.isConnected()) {
		// if (mConnectionResult == null) {
		// mConnectionProgressDialog.show();
		// } else {
		// try {
		// mConnectionResult.startResolutionForResult(this,
		// REQUEST_CODE_RESOLVE_ERR);
		// } catch (SendIntentException e) {
		// // Nouvelle tentative de connexion
		// mConnectionResult = null;
		// mPlusClient.connect();
		// }
		// }
		// }

		switch (v.getId()) {
		case R.id.sign_in_button:
			int available = GooglePlayServicesUtil
					.isGooglePlayServicesAvailable(this);
			if (available != ConnectionResult.SUCCESS) {
				showDialog(DIALOG_GET_GOOGLE_PLAY_SERVICES);
				return;
			}

			try {

				mConnectionResult.startResolutionForResult(this,
						REQUEST_CODE_SIGN_IN);
			} catch (IntentSender.SendIntentException e) {
				// Fetch a new result to start.
				mPlusClient.connect();
			}
			break;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id != DIALOG_GET_GOOGLE_PLAY_SERVICES) {
			return super.onCreateDialog(id);
		}

		int available = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (available == ConnectionResult.SUCCESS) {
			return null;
		}
		if (GooglePlayServicesUtil.isUserRecoverableError(available)) {
			return GooglePlayServicesUtil.getErrorDialog(available, this,
					REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES);
		}
		return new AlertDialog.Builder(this).setMessage("erreur")
				.setCancelable(true).create();
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		mConnectionResult = arg0;
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		Person person = Plus.PeopleApi.getCurrentPerson(mPlusClient);
		String accountName = Plus.AccountApi.getAccountName(mPlusClient);

		Intent intent = new Intent(ConnectActivity.this, FormActivity.class);

		intent.putExtra("name", person.getDisplayName());
		intent.putExtra("id", person.getId());
		intent.putExtra("url", person.getImage().getUrl());
		intent.putExtra("email", accountName);
		intent.putExtra("from", "google");
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_from_bottom,
				R.anim.slide_out_to_bottom);
		finish();

	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart() {
		super.onStart();
		mPlusClient.connect();
	}

	@Override
	public void onStop() {
		mPlusClient.disconnect();
		super.onStop();
	}

	private static Session openActiveSession(Activity activity,
			boolean allowLoginUI, Session.StatusCallback callback,
			List<String> permissions) {
		Session.OpenRequest openRequest = new Session.OpenRequest(activity)
				.setPermissions(permissions).setCallback(callback);
		Session session = new Session.Builder(activity).build();
		if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState())
				|| allowLoginUI) {
			Session.setActiveSession(session);
			session.openForRead(openRequest);
			return session;
		}
		return null;
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
