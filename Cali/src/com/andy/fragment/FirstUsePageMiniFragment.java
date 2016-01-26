package com.andy.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andy.cali.R;

@SuppressLint("NewApi")
public class FirstUsePageMiniFragment extends Fragment {

	public static final String ARG_PAGE = "page";

	private int mPageNumber;

	ImageView mImageView;

	public static  FirstUsePageMiniFragment create(int pageNumber) {
		FirstUsePageMiniFragment fragment = new FirstUsePageMiniFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public FirstUsePageMiniFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_first_use_page2, container, false);
		mImageView = (ImageView) rootView.findViewById(R.id.mobileFond);

		switch (mPageNumber) {
		case 0:
			mImageView.setImageResource(R.drawable.s1);
			break;
		case 1:
			mImageView.setImageResource(R.drawable.s2);
			break;
		case 2:
			mImageView.setImageResource(R.drawable.s3);
			break;
		case 3:
			mImageView.setImageResource(R.drawable.s4);
			break;
		}

		return rootView;
	}
	public int getPageNumber() {
		return mPageNumber;
	}
}
