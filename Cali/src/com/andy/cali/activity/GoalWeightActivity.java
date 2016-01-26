package com.andy.cali.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.cali.R;
import com.andy.entities.User;
import com.andy.sqlite.UserBDD;
import com.dafruits.android.library.widgets.ExtendedListView;
import com.dafruits.android.library.widgets.ExtendedListView.OnPositionChangedListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class GoalWeightActivity extends Activity implements
		OnPositionChangedListener {

	private ExtendedListView list;
	ArrayAdapter<String> adapter;
	List<String> l;
	List<User> l1;
	TextView t1;
	int index1;
	String weight;
	String height;
	ImageView index;
	int interpret;
	int wished;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goalweight_layout);

		l1 = new ArrayList<User>();
		UserBDD us = new UserBDD(getApplicationContext());
		us.open();
		l1 = us.selectAll();
		us.close();
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.statusbar_bg);
	

		weight = l1.get(0).getWeight();
		height = l1.get(0).getHeight();
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1E88E5")));

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setTitle(getResources().getString(R.string.goal11));
		getActionBar().setIcon(R.drawable.cursor_red);

		index = (ImageView) findViewById(R.id.index);
		list = (ExtendedListView) findViewById(R.id.weightlist);
		// t1 =(TextView)findViewById(R.id.textView4);
		l = new ArrayList<String>();

		for (int i = 1; i < 200; i++) {
			l.add(i + " Kg");

		}

		adapter = new ArrayAdapter<String>(this, R.layout.list_item, l);
		list.setAdapter(adapter);
		list.setCacheColorHint(Color.TRANSPARENT);
		list.setSelection(Integer.valueOf(weight));

		list.setOnPositionChangedListener(this);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case android.R.id.home:
			finish();

			break;
		case R.id.action_next:
			Intent i = new Intent(GoalWeightActivity.this,
					GoalWeight2Activity.class);
			i.putExtra("wished", "" + wished);
			startActivityForResult(i, 5);

			break;
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.next, menu);
		return true;
	}

	@SuppressLint("DefaultLocale")
	@Override
	public void onPositionChanged(ExtendedListView listView, int position,
			View scrollBarPanel) {
		// TODO Auto-generated method stub
		wished = position + 2;

		float heigh1 = Float.valueOf(height);
		float result = calculateBMI(wished, heigh1);

		float resultfinal = result * 10000;
		interpret = interpretBMI(resultfinal);
		// Toast.makeText(getApplicationContext(), resultfinal+"", 1000).show();
		switch (interpret) {
		case 1:
			index.setImageResource(R.drawable.a1);
			break;
		case 2:
			index.setImageResource(R.drawable.a2);
			break;
		case 3:
			index.setImageResource(R.drawable.a3);
			break;
		case 4:
			index.setImageResource(R.drawable.a4);
			break;
		case 5:
			index.setImageResource(R.drawable.a5);
			break;
		case 6:
			index.setImageResource(R.drawable.a6);
			break;
		case 7:
			index.setImageResource(R.drawable.a7);
			break;
		case 8:
			index.setImageResource(R.drawable.a8);
			break;
		case 9:
			index.setImageResource(R.drawable.a9);
			break;

		}

	}

	private float calculateBMI(float weight, float height) {

		return (float) (weight / (height * height));

	}

	// interpret what BMI means
	private int interpretBMI(double resultfinal) {

		if (resultfinal <= 16.0) {
			index1 = 1;

		} else if (resultfinal > 16.0 && resultfinal <= 17.0) {
			index1 = 2;

		} else if (resultfinal > 17.0 && resultfinal <= 18.5) {
			index1 = 3;
			// return "Normal weight";
		} else if (resultfinal > 18.5 && resultfinal <= 20.9) {
			index1 = 4;

		} else if (resultfinal > 21.0 && resultfinal <= 22.9) {
			index1 = 5;

		} else if (resultfinal > 23.9 && resultfinal <= 24.9) {
			index1 = 6;

		} else if (resultfinal > 25 && resultfinal <= 29.9) {
			index1 = 7;

		} else if (resultfinal > 30 && resultfinal <= 34.9) {
			index1 = 8;

		} else if (resultfinal >= 35) {
			index1 = 9;

		}
		return index1;

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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 5 && resultCode == RESULT_OK) {
			Intent intent = new Intent();

			setResult(RESULT_OK, intent);

			super.finish();
		} else {

			super.finish();
		}

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
