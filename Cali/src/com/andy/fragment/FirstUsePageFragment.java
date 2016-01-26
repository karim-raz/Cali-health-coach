package com.andy.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.cali.R;


public class FirstUsePageFragment extends Fragment {

	public static final String ARG_PAGE = "page";

	private int mPageNumber;

	TextView mTextView;
	ImageView mImageView;
	LinearLayout mContainer;

	
	public static  FirstUsePageFragment create(int pageNumber) {
		FirstUsePageFragment fragment = new FirstUsePageFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public FirstUsePageFragment() {
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
				R.layout.fragment_first_use_page, container, false);
		mContainer = (LinearLayout) rootView.findViewById(R.id.contaier);
		mTextView = (TextView) rootView.findViewById(R.id.FirstUseText);

		switch (mPageNumber) {
		case 0:
			mTextView
					.setText(getResources().getString(R.string.first1));
			mContainer.setBackgroundResource(R.drawable.first_use44);
			break;
		case 1:
			mTextView
					.setText(getResources().getString(R.string.first2));
			mContainer.setBackgroundResource(R.drawable.first_use4);
			break;
		case 2:
			mTextView
					.setText(getResources().getString(R.string.first3));
			mContainer.setBackgroundResource(R.drawable.first_use44);
			break;
		case 3:
			mTextView
			.setText(getResources().getString(R.string.first4));
	mContainer.setBackgroundResource(R.drawable.first_use4);
			break;
			
		}

		return rootView;
	}

	/**
	 * Returns the page number represented by this fragment object.
	 */
	public int getPageNumber() {
		return mPageNumber;
	}
}
