package com.andy.cali.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.adapter.CustomAdapterLogFirst;
import com.andy.cali.R;
import com.andy.sqlite.LogBDD;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class LogDietsfirstActivity extends Activity {
	ListView listlog;
	List<ListViewItem> items;
	TextView date;
	String month;
	int daymonth;
	String today;
	CustomAdapterLogFirst adapter2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_first_layout);

		Calendar c = Calendar.getInstance();
		daymonth = c.get(Calendar.DAY_OF_MONTH);
		int thisMonth = c.get(Calendar.MONTH) + 1;
		switch (thisMonth) {
		case 1:
			month = getString(R.string.log11);
			break;
		case 2:
			month = getString(R.string.log12);
			break;
		case 3:
			month = getString(R.string.log13);
			break;
		case 4:
			month = getString(R.string.log14);
			break;
		case 5:
			month = getString(R.string.log15);
			break;
		case 6:
			month = getString(R.string.log16);
			break;
		case 7:
			month = getString(R.string.log17);
			break;
		case 8:
			month = getString(R.string.log18);
			break;
		case 9:
			month = getString(R.string.log19);
			break;
		case 10:
			month = getString(R.string.log191);
			break;
		case 11:
			month = getString(R.string.log192);
			break;
		case 12:
			month = getString(R.string.log193);
			break;

		}

		date = (TextView) findViewById(R.id.current);
		today = daymonth + " " + month;
		date.setText(today);

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.statusbar_bg);
		listlog = (ListView) findViewById(R.id.daylog);
		getActionBar().setTitle(getString(R.string.log194));
		getActionBar().setIcon(R.drawable.cursor_red);

		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1E88E5")));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		items = new ArrayList<LogDietsfirstActivity.ListViewItem>();
		items.add(new ListViewItem() {
			{
				ThumbnailResource = R.drawable.diet;
				Title = getString(R.string.log195);

			}
		});
		items.add(new ListViewItem() {
			{
				ThumbnailResource = R.drawable.diet;
				Title = getString(R.string.log196);

			}
		});
		items.add(new ListViewItem() {
			{
				ThumbnailResource = R.drawable.diet;
				Title = getString(R.string.log197);

			}
		});
		items.add(new ListViewItem() {
			{
				ThumbnailResource = R.drawable.diet;
				Title = getString(R.string.log198);

			}
		});
		items.add(new ListViewItem() {
			{
				ThumbnailResource = R.drawable.diet;
				Title = getString(R.string.log199);

			}
		});
		items.add(new ListViewItem() {
			{
				ThumbnailResource = R.drawable.diet;
				Title = getString(R.string.log19991);

			}
		});
		adapter2 = new CustomAdapterLogFirst(this, items);

		listlog.setAdapter(adapter2);
		listlog.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					Intent intent = new Intent(LogDietsfirstActivity.this,
							LogDietsSecondActivity.class);
					intent.putExtra("diet", getString(R.string.log195));
					startActivityForResult(intent, 7);

					break;
				case 1:
					Intent intent1 = new Intent(LogDietsfirstActivity.this,
							LogDietsSecondActivity.class);
					intent1.putExtra("diet", getString(R.string.log196));
					startActivityForResult(intent1, 7);

					break;
				case 2:
					Intent intent2 = new Intent(LogDietsfirstActivity.this,
							LogDietsSecondActivity.class);
					intent2.putExtra("diet", getString(R.string.log197));
					startActivityForResult(intent2, 7);

					break;
				case 3:
					Intent intent3 = new Intent(LogDietsfirstActivity.this,
							LogDietsSecondActivity.class);
					intent3.putExtra("diet", getString(R.string.log198));
					startActivityForResult(intent3, 7);

					break;
				case 4:
					Intent intent4 = new Intent(LogDietsfirstActivity.this,
							LogDietsSecondActivity.class);
					intent4.putExtra("diet", getString(R.string.log199));
					startActivityForResult(intent4, 7);

					break;
				case 5:
					Intent intent5 = new Intent(LogDietsfirstActivity.this,
							LogDietsSecondActivity.class);
					intent5.putExtra("diet", getString(R.string.log19991));
					startActivityForResult(intent5, 7);

					break;

				}
			}
		});
	}

	public class ListViewItem {
		public int ThumbnailResource;
		public String Title;

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		LogBDD ldBDD = new LogBDD(getApplicationContext());
		ldBDD.open();
		// dBDD.removeAllArticles();
		ldBDD.removeAllArticles();
		ldBDD.close();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case android.R.id.home:
			LogBDD ldBDD = new LogBDD(getApplicationContext());
			ldBDD.open();
			// dBDD.removeAllArticles();
			ldBDD.removeAllArticles();
			ldBDD.close();
			finish();
			break;

		}
		return super.onOptionsItemSelected(item);

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

		if (requestCode == 7 && resultCode == RESULT_OK) {
			Intent intent = new Intent();

			setResult(RESULT_OK, intent);

			super.finish();
		}
		// else{
		//
		// super.finish();
		// }

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
