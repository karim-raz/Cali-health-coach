package com.andy.cali.activity;

import java.util.ArrayList;
import java.util.List;

import uk.me.lewisdeane.lnavigationdrawer.NavigationItem;
import uk.me.lewisdeane.lnavigationdrawer.NavigationListView;
import uk.me.lewisdeane.lnavigationdrawer.NavigationListView.NavigationItemClickListener;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.adapter.CustomAdapterContainer;
import com.andy.cali.R;
import com.andy.entities.User;
import com.andy.fragment.AboutFragment;
import com.andy.fragment.CaliFragment;
import com.andy.fragment.LogBookFragment;
import com.andy.fragment.WeightInFragment;
import com.andy.sqlite.UserBDD;
import com.gc.materialdesign.widgets.DialogQuit;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class ContainerActivity extends ActionBarActivity {
	DialogQuit dialog;
	private DrawerLayout mDrawerLayout;
	// private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	// boolean drawerArrowColor;
	TextView name;
	CustomAdapterContainer adapter2;
	UserBDD userBDD;

	String age;
	String height;
	String weight;
	String gender;
	Bundle bundle;
	Fragment cali;
	String id;
	String namee;
	String toshow;
	NavigationListView navigationListView;
	List<ListViewItem> items;
	private ActionBar actionBar;
	LinearLayout drawerX;
	List<User> l5;
	String my_edittext_preference;
	String my_edittext_preference2;
	TextView tv1;
	TextView tv2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Intent intent = getIntent();
		// toshow = intent.getStringExtra("toshow");
		setContentView(R.layout.container_layout);

		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		LayoutInflater mInflater;
		Context context = getApplicationContext();
		mInflater = LayoutInflater.from(context);
		View view = mInflater.inflate(R.layout.decor, null);

		// HACK: "steal" the first child of decor view
		ViewGroup decor = (ViewGroup) getWindow().getDecorView();
		View child = decor.getChildAt(0);
		decor.removeView(child);
		RelativeLayout container = (RelativeLayout) view.findViewById(R.id.lol);

		container.addView(child, 0);
		view.findViewById(R.id.navdrawe1).setPadding(0, getStatusBarHeight(),
				0, 0);

		// Make the drawer replace the first child
		decor.addView(view);
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.statusbar_bg);
		drawerX = (LinearLayout) findViewById(R.id.navdrawe1);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ImageView myimage = (ImageView) findViewById(R.id.photo_g_plus);
		tv1 = (TextView) findViewById(R.id.header_tv);
		tv2 = (TextView) findViewById(R.id.header_tv2);

		l5 = new ArrayList<User>();
		userBDD = new UserBDD(getApplicationContext());
		userBDD.open();

		l5 = userBDD.selectAll();

		userBDD.close();

		tv1.setText(l5.get(0).getName());
		tv2.setText(l5.get(0).getEmail());
		byte[] bitmapdata = l5.get(0).getLogo();
		// if bitmapdata is the byte array then getting bitmap goes like this

		Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0,
				bitmapdata.length);
		myimage.setImageBitmap(bitmap);
		// mDrawerList = (ListView) findViewById(R.id.navdrawer);
		navigationListView = (NavigationListView) findViewById(R.id.navdrawer);
		navigationListView.addNavigationItem(new NavigationItem(getResources()
				.getString(R.string.cont1), R.drawable.home, true));
		navigationListView.addNavigationItem(new NavigationItem(getResources()
				.getString(R.string.cont2), R.drawable.scale, false));
		navigationListView.addNavigationItem(new NavigationItem(getResources()
				.getString(R.string.cont3), R.drawable.book, false));
		// navigationListView.addNavigationItem(new
		// NavigationItem(getResources()
		// .getString(R.string.cont4), R.drawable.setting, false));
		navigationListView.addNavigationItem(new NavigationItem(getResources()
				.getString(R.string.cont5), R.drawable.about, false));
		// Image, text and ability to set selected.

		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				drawerArrow, R.string.drawer_open, R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();

		// items = new ArrayList<ContainerActivity.ListViewItem>();
		// items.add(new ListViewItem() {
		// {
		// ThumbnailResource = R.drawable.home;
		// Title = "Cali";
		//
		// }
		// });
		//
		// items.add(new ListViewItem() {
		// {
		// ThumbnailResource = R.drawable.scale;
		// Title = "Weight in";
		//
		// }
		// });
		// items.add(new ListViewItem() {
		// {
		// ThumbnailResource = R.drawable.book;
		// Title = "LogBook";
		//
		// }
		// });
		// items.add(new ListViewItem() {
		// {
		// ThumbnailResource = R.drawable.setting;
		// Title = "Settings";
		//
		// }
		// });
		// items.add(new ListViewItem() {
		// {
		// ThumbnailResource = R.drawable.help;
		// Title = "About";
		//
		// }
		// });

		// adapter2 = new CustomAdapterContainer(this, items);
		//
		// navigationListView.setAdapter(adapter2);
		// navigationListView
		// .setOnItemClickListener(new AdapterView.OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		navigationListView
				.setNavigationItemClickListener(new NavigationItemClickListener() {
					@Override
					public void onNavigationItemSelected(String item,
							ArrayList<NavigationItem> items, int position) {
						// Do your stuff when an item is selected, do not worry
						// about changing the colour or anything as it is
						// handled for you.

						switch (position) {
						case 0:

							cali = new CaliFragment();

							getFragmentManager().beginTransaction()

							.replace(R.id.lol, cali).commit();
							mDrawerLayout.closeDrawer(drawerX);

							break;
						case 1:

							getFragmentManager().beginTransaction()
									.replace(R.id.lol, new WeightInFragment())
									.commit();
							mDrawerLayout.closeDrawer(drawerX);
							break;
						case 2:

							getFragmentManager().beginTransaction()
									.replace(R.id.lol, new LogBookFragment())
									.commit();
							mDrawerLayout.closeDrawer(drawerX);
							break;
						case 3:

							getFragmentManager().beginTransaction()
									.replace(R.id.lol, new AboutFragment())
									.commit();
							mDrawerLayout.closeDrawer(drawerX);

							break;

						}

					}
				});

		cali = new CaliFragment();

		getFragmentManager().beginTransaction()

		.replace(R.id.lol, cali, "cali").commit();
		mDrawerLayout.closeDrawer(drawerX);

	}

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(drawerX)) {
				mDrawerLayout.closeDrawer(drawerX);
			} else {
				mDrawerLayout.openDrawer(drawerX);

			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.global, menu);
		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
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

	public class ListViewItem {
		public int ThumbnailResource;
		public String Title;

	}

	@Override
	public void onBackPressed() {
		dialog = new DialogQuit(ContainerActivity.this, getResources()
				.getString(R.string.doyou), "");
		dialog.setOnAcceptButtonClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		dialog.setOnCancelButtonClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
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
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		invalidateOptionsMenu();
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
