package com.andy.cali.activity;

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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.cali.R;
import com.andy.entities.Program;
import com.andy.sqlite.ProgBDD;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class GoalWeight2Activity extends Activity implements OnClickListener {
	ArrayAdapter<String> adapter;
	List<String> l;
	int intensity;
	TextView choice1, choice2, choice3;
	ImageView c1, c2, c3;
	String wished;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.goalweight_layout2);
	
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1E88E5")));

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.statusbar_bg);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setTitle(getResources().getString(R.string.goal11));
		getActionBar().setIcon(R.drawable.cursor_red);
		c1 = (ImageView) findViewById(R.id.ch01);
		c2 = (ImageView) findViewById(R.id.ch02);
		c3 = (ImageView) findViewById(R.id.ch03);

		choice1 = (TextView) findViewById(R.id.ch0);
		choice2 = (TextView) findViewById(R.id.ch1);
		choice3 = (TextView) findViewById(R.id.ch3);

		
		choice1.setTextColor(this.getResources().getColor(R.color.gray));
		choice2.setTextColor(this.getResources().getColor(R.color.blue));
		choice3.setTextColor(this.getResources().getColor(R.color.gray));
		c2.setVisibility(View.VISIBLE);
		intensity = 0;
		Intent intent = getIntent();
		wished = intent.getStringExtra("wished");
		choice1.setOnClickListener(this);
		choice2.setOnClickListener(this);
		choice3.setOnClickListener(this);

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
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case android.R.id.home:

			super.finish();

			break;
		case R.id.action_check:
			ProgBDD pBDD2;
			pBDD2 = new ProgBDD(getApplicationContext());

			pBDD2.open();
			pBDD2.removeAllArticles();
			pBDD2.close();

			ProgBDD pBDD;
			pBDD = new ProgBDD(getApplicationContext());

			pBDD.open();
			if (intensity == 0) {
				Program pr = new Program(wished, "easy");
				pBDD.insertTop(pr);
				pBDD.close();
			}
			if (intensity == 1) {
				Program pr = new Program(wished, "average");
				pBDD.insertTop(pr);
				pBDD.close();
			}
			if (intensity == 2) {
				Program pr = new Program(wished, "intense");
				pBDD.removeAllArticles();
				pBDD.insertTop(pr);
				pBDD.close();
			}
			// Intent intent = new Intent(GoalWeight2Activity.this,
			// ContainerActivity.class);
			// intent.putExtra("toshow", "1");

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
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.check, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ch0:
			intensity = 0;
			choice1.setTextColor(this.getResources().getColor(R.color.blue));
			choice2.setTextColor(this.getResources().getColor(R.color.gray));
			choice3.setTextColor(this.getResources().getColor(R.color.gray));
			c1.setVisibility(View.VISIBLE);
			c2.setVisibility(View.GONE);
			c3.setVisibility(View.GONE);
			break;
		case R.id.ch1:
			intensity = 1;
			choice1.setTextColor(this.getResources().getColor(R.color.gray));
			choice2.setTextColor(this.getResources().getColor(R.color.blue));
			choice3.setTextColor(this.getResources().getColor(R.color.gray));
			c1.setVisibility(View.GONE);
			c2.setVisibility(View.VISIBLE);
			c3.setVisibility(View.GONE);

			break;
		case R.id.ch3:
			intensity = 2;
			choice1.setTextColor(this.getResources().getColor(R.color.gray));
			choice2.setTextColor(this.getResources().getColor(R.color.gray));
			choice3.setTextColor(this.getResources().getColor(R.color.blue));
			c1.setVisibility(View.GONE);
			c2.setVisibility(View.GONE);
			c3.setVisibility(View.VISIBLE);

			break;

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
