package com.andy.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;

import com.andy.cali.R;

@SuppressLint("NewApi")
public class StepsParentFragment extends Fragment {
	// DJO
	Switch switchLanguge;
	static String x;
	static String y;
	public static final String TAG = StepsParentFragment.class.getSimpleName();

	public static StepsParentFragment newInstance() {
		return new StepsParentFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		 View view =inflater.inflate(R.layout.fragment_parent_steps, container,
				false);
		
		return view;
	}

	@SuppressLint("NewApi")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		getActivity().getActionBar().setIcon(R.drawable.cursor_red);


		ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
		
		mViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

		PagerTabStrip pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.pagerTabStrip);
		pagerTabStrip.setDrawFullUnderline(true);
		pagerTabStrip.setTabIndicatorColor(Color.parseColor("#ffffff"));
		pagerTabStrip.setTextColor(Color.parseColor("#ffffff"));
		x= getString(R.string.log19992);
		y= getString(R.string.stepsd8);
	}

	public static class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new Fragment();
			switch (position) {
			case 0:
				fragment = new DayStepsDetailsFragment();
				break;
			case 1:
				fragment = new WeekStepsDetailsFragment();
				break;
			case 2:
				fragment = new MonthStepsDetailsFragment();
				break;
			

			}
			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {

			String title = "";

			switch (position) {
			case 0:
				title = x;

				break;
			case 1:
				title = y;
				break;
			case 2:
				title = "This Month";
				break;

			

			}
			return title;
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		// Always call the superclass method first

		// Activity being restarted from stopped state
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

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
