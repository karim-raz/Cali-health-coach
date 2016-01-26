/*
 * Copyright (C) 2013 Manuel Peinado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.andy.cali.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.cali.R;
import com.andy.entities.Tips;
import com.andy.sqlite.TipsBDD;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

public class TipsActivity extends Activity {
	TipsBDD tpBDD;
	List<Tips> l;
	TextView Title1;
	TextView Title2;
	TextView Title3;
	TextView Desc1;
	TextView Desc2;
	TextView Desc3;
	ImageView photot;

	@SuppressLint({ "InflateParams", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setTitle(getResources().getString(R.string.tips1));
		getActionBar().setIcon(R.drawable.cursor_red);
		l = new ArrayList<Tips>();
		tpBDD = new TipsBDD(getApplicationContext());
		tpBDD.open();

		l = tpBDD.selectAll();

		tpBDD.close();
		byte[] bitmapdata = l.get(0).getPhotot();
		Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0,
				bitmapdata.length);

		FadingActionBarHelper helper = new FadingActionBarHelper()
				.actionBarBackground(R.drawable.ab_background)
				.headerLayout(R.layout.header)
				.contentLayout(R.layout.tips_layout);
		setContentView(helper.createView(this));
		helper.initActionBar(this);
		photot = (ImageView) findViewById(R.id.image_header);
		photot.setImageBitmap(bitmap);

		// change dinamicly picturess

		Title1 = (TextView) findViewById(R.id.titletips1);
		Title2 = (TextView) findViewById(R.id.titletips2);
		Title3 = (TextView) findViewById(R.id.titletips3);
		Desc1 = (TextView) findViewById(R.id.desctips1);
		Desc2 = (TextView) findViewById(R.id.desctips2);
		Desc3 = (TextView) findViewById(R.id.desctips3);
		Title1.setText(l.get(0).getTitle1());
		Desc1.setText(l.get(0).getDesc1());
		Title2.setText(l.get(0).getTitle2());
		Desc2.setText(l.get(0).getDesc2());
		Title3.setText(l.get(0).getTitle3());
		Desc3.setText(l.get(0).getDesc3());

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

			finish();

			break;
		case R.id.action_done:

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
		inflater.inflate(R.menu.done, menu);
		return true;
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
