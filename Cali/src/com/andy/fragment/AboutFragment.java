package com.andy.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.andy.cali.R;

public class AboutFragment extends Fragment {
	ImageView fb1, fb2, go1, go2;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.about_layout, container,
				false);
		
	
		fb1 = (ImageView) rootView.findViewById(R.id.fb1);
		fb2 = (ImageView) rootView.findViewById(R.id.fb2);
		go1 = (ImageView) rootView.findViewById(R.id.go1);
		go2 = (ImageView) rootView.findViewById(R.id.go2);
		fb1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent facebookIntent = getOpenFacebookIntent(getActivity());
				startActivity(facebookIntent);
			}
		});
		fb2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent facebookIntent = getOpenFacebookIntent2(getActivity());
				startActivity(facebookIntent);
			}
		});
		go1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/1/+manaichiheb/posts")));
			}
		});
		go2.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/1/109043390795979915242/posts")));
			}
		});
		getActivity().getActionBar().setIcon(R.drawable.cursor_red);

		getActivity().getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1E88E5")));
		getActivity().getActionBar().setTitle(getResources().getString(R.string.cont5));
		

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Add your menu entries here
		inflater.inflate(R.menu.global, menu);
		super.onCreateOptionsMenu(menu, inflater);
	
	}
	
	public static Intent getOpenFacebookIntent(Context context) {

	    try {
	        context.getPackageManager()
	                .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
	        return new Intent(Intent.ACTION_VIEW,//1374146298
	        		//100001554452368
	                Uri.parse("fb://profile/100001554452368")); //Trys to make intent with FB's URI
	    } catch (Exception e) {
	        return new Intent(Intent.ACTION_VIEW,
	                Uri.parse("https://www.facebook.com/chimanai")); //catches and opens a url to the desired page
	    }
	}
	public static Intent getOpenFacebookIntent2(Context context) {

	    try {
	        context.getPackageManager()
	                .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
	        return new Intent(Intent.ACTION_VIEW,
	                Uri.parse("fb://profile/1374146298")); //Trys to make intent with FB's URI
	    } catch (Exception e) {
	        return new Intent(Intent.ACTION_VIEW,
	                Uri.parse("https://www.facebook.com/kimard3?fref=ts")); //catches and opens a url to the desired page
	    }
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
