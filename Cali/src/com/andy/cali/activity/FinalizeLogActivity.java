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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.adapter.CustomAdapterFinalize;
import com.andy.cali.R;
import com.andy.entities.Diet;
import com.andy.entities.LogContainer;
import com.andy.sqlite.DietBDD;
import com.andy.sqlite.LogBDD;
import com.gc.materialdesign.views.ButtonFloatSmall;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class FinalizeLogActivity extends Activity {
	ListView loged;
	List<ListViewItem> items;
	TextView datelogged;
	TextView textlogged;
	CustomAdapterFinalize adapter;
	LogBDD ldBDD;

	List<LogContainer> l;
	List<Diet> l1;
	String name;
	String month;
	int daymonth;
	String today;
	TextView max;
	String value;
	ButtonFloatSmall more;
	String diet;
	int total = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

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

		setContentView(R.layout.finalize_layout);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1E88E5")));
		getActionBar().setTitle("");
		getActionBar().setIcon(R.drawable.cursor_red);
	

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.statusbar_bg);
		Intent intena = getIntent();
		diet = intena.getStringExtra("diet");
		loged = (ListView) findViewById(R.id.listlogged);
		textlogged = (TextView) findViewById(R.id.textlogged);
		textlogged.setText(diet);
		datelogged = (TextView) findViewById(R.id.datelogged);
		today = getString(R.string.final1) + " " + daymonth + " " + month;
		datelogged.setText(today);
		max = (TextView) findViewById(R.id.maxl);
		more = (ButtonFloatSmall) findViewById(R.id.more);
		more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(FinalizeLogActivity.this,
				// LogDietsSecondActivity.class);
				// intent.putExtra("diet", diet);
				// startActivity(intent);
				finish();
			}
		});

		l = new ArrayList<LogContainer>();
		ldBDD = new LogBDD(getApplicationContext());
		ldBDD.open();

		l = ldBDD.selectAll();

		ldBDD.close();
		items = new ArrayList<FinalizeLogActivity.ListViewItem>();
		for (LogContainer country : l) {
			name = country.getName();
			value = country.getValue();
			total = total + Integer.valueOf(value);

			items.add(new ListViewItem() {
				{
					text1 = name;
					text2 = value;

				}
			});

		}
		adapter = new CustomAdapterFinalize(getApplicationContext(), items);

		loged.setAdapter(adapter);
		max.setText("" + total + " Cal");
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

		public String text1;
		public String text2;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case android.R.id.home:
			finish();

			break;
		case R.id.action_check:
			DietBDD dtBDD = new DietBDD(getApplicationContext());
			l1 = new ArrayList<Diet>();

			dtBDD.open();

			l1 = dtBDD.selectAll();

			dtBDD.close();

			DietBDD dtBDD2 = new DietBDD(getApplicationContext());

			dtBDD2.open();

			dtBDD2.removeAllArticles();

			dtBDD2.close();
			int value = Integer.valueOf(l1.get(0).getMax()) + total;
			Diet d = new Diet(String.valueOf(value), "0");
			DietBDD dtBDD1 = new DietBDD(getApplicationContext());
			dtBDD1.open();
			dtBDD1.insertTop(d);
			dtBDD1.close();

			ldBDD = new LogBDD(getApplicationContext());
			ldBDD.open();
			// program pr = new program(wished, "intense");
			ldBDD.removeAllArticles();
			// pBDD.insertTop(pr);
			ldBDD.close();
			// Intent intent = new Intent(FinalizeLogActivity.this,
			// ContainerActivity.class);
			// intent.putExtra("toshow", "2");
			// startActivity(intent);
			Intent intent = new Intent();

			setResult(RESULT_OK, intent);

			super.finish();

			break;
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.check, menu);
		return true;
	}
}
