package com.andy.cali.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.adapter.CustomAdapterLogSecond;
import com.andy.adapter.CustomAdapterLogSecond2;
import com.andy.cali.R;
import com.andy.entities.Details;
import com.andy.entities.LogContainer;
import com.andy.entities.Nutrition;
import com.andy.sqlite.DetailsBDD;
import com.andy.sqlite.LogBDD;
import com.andy.sqlite.NutritionBDD;
import com.gc.materialdesign.views.ButtonFlat;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.parse.ParseObject;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class LogDietsSecondActivity extends Activity {
	// ArrayList<String> values;
	ListView l1;
	String namechosen;
	String valuechosen;
	ListView listcal;
	List<ListViewItem> items;
	List<ListViewItem2> items2;
	// ArrayAdapter<String> ad;
	SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;
	List<ParseObject> ob;
	// nutrition
	String name;
	String Id_N;
	ArrayList<LogDietsSecondActivity.ListViewItem> tempArrayList;
	String Cat_N;
	// nutrition test
	String namet;
	String Id_Nt;
	String Cat_Nt;
	// details
	String Id_NC;
	String Part1;
	String Part2;
	String Part3;
	String Part4;
	String Val1;
	String Val2;
	String Val3;
	String Val4;
	// details test
	String Id_NCt;
	String Part1t;
	String Part2t;
	String Part3t;
	String Part4t;
	String Val1t;
	String Val2t;
	String Val3t;
	String Val4t;
	CustomAdapterLogSecond adapter;
	CustomAdapterLogSecond2 adapter2;
	EditText search;
	List<Nutrition> l;
	List<Details> l2;
	NutritionBDD nBDD;
	DetailsBDD dBDD;
	LogBDD ldBDD;
	String diet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_second_layout);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			RelativeLayout ll = (RelativeLayout) findViewById(R.id.log548);
			ll.setPadding(0, 150, 0, 0);
			setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.statusbar_bg);
		Intent intent = getIntent();
		diet = intent.getStringExtra("diet");

		getActionBar().setIcon(R.drawable.cursor_red);
		getActionBar().setTitle(getString(R.string.log194));
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1E88E5")));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		
		search = (EditText) findViewById(R.id.editTextch);
		l1 = (ListView) findViewById(R.id.lista);

		l2 = new ArrayList<Details>();
		dBDD = new DetailsBDD(getApplicationContext());
		dBDD.open();

		l2 = dBDD.selectAll();

		dBDD.close();
		l = new ArrayList<Nutrition>();
		nBDD = new NutritionBDD(getApplicationContext());
		nBDD.open();

		l = nBDD.selectAll();

		nBDD.close();
		items = new ArrayList<LogDietsSecondActivity.ListViewItem>();
		for (Nutrition country : l) {
			name = country.getName_N();
			Id_N = country.getId_N();
			Cat_N = country.getCategory_N();
			items.add(new ListViewItem() {
				{
					ThumbnailResource = R.drawable.diet;
					Title = name;

				}
			});

		}
		adapter = new CustomAdapterLogSecond(getApplicationContext(), items);
		swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
				adapter);
		swingBottomInAnimationAdapter.setAbsListView(l1);

		l1.setAdapter(swingBottomInAnimationAdapter);

		l1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				namet = items.get(position).Title;

				for (Nutrition n : l) {
					if (namet.equals(n.getName_N())) {
						Id_Nt = n.getId_N();

						Cat_Nt = n.getCategory_N();
						for (Details d : l2) {

							if (d.getId_NC().equals(Id_Nt)) {

								Part1t = d.getPart1();
								Part2t = d.getPart2();
								Part3t = d.getPart3();
								Part4t = d.getPart4();
								Val1t = d.getVal1();
								Val2t = d.getVal2();
								Val3t = d.getVal3();
								Val4t = d.getVal4();
								final Dialog dialog = new Dialog(
										LogDietsSecondActivity.this);
								// Include dialog.xml file
								dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
								dialog.setContentView(R.layout.dialog1);
								// Set dialog title
								listcal = (ListView) dialog
										.findViewById(R.id.listc);
								TextView namec = (TextView) dialog
										.findViewById(R.id.titlec);
								TextView typec = (TextView) dialog
										.findViewById(R.id.typec);
								namec.setText(namet);
								typec.setText(Cat_Nt);
								items2 = new ArrayList<LogDietsSecondActivity.ListViewItem2>();

								// set values for custom dialog components -
								// text, image and button
								items2.add(new ListViewItem2() {
									{
										text1 = Part1t;
										text2 = Val1t;

									}
								});
								items2.add(new ListViewItem2() {
									{
										text1 = Part2t;
										text2 = Val2t;

									}
								});
								items2.add(new ListViewItem2() {
									{
										text1 = Part3t;
										text2 = Val3t;

									}
								});
								items2.add(new ListViewItem2() {
									{
										text1 = Part4t;
										text2 = Val4t;

									}
								});
								adapter2 = new CustomAdapterLogSecond2(
										getApplicationContext(), items2);

								listcal.setAdapter(adapter2);

								dialog.show();

								ButtonFlat declineButton = (ButtonFlat) dialog
										.findViewById(R.id.canc);
								// if decline button is clicked, close the
								// custom dialog
								declineButton
										.setOnClickListener(new OnClickListener() {
											@Override
											public void onClick(View v) {
												// Close dialog
												dialog.dismiss();
											}
										});
								listcal.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										// TODO Auto-generated method stub

										switch (position) {
										case 0:
											LogContainer dt = new LogContainer(
													namet, Val1t);

											ldBDD = new LogBDD(
													getApplicationContext());
											ldBDD.open();
											// dBDD.removeAllArticles();
											ldBDD.insertTop(dt);
											ldBDD.close();
											Intent intent = new Intent(
													LogDietsSecondActivity.this,
													FinalizeLogActivity.class);
											intent.putExtra("diet", diet);
											startActivityForResult(intent, 8);
											dialog.dismiss();
											break;
										case 1:
											LogContainer dt1 = new LogContainer(
													namet, Val2t);

											ldBDD = new LogBDD(
													getApplicationContext());
											ldBDD.open();
											// dBDD.removeAllArticles();
											ldBDD.insertTop(dt1);
											ldBDD.close();
											Intent intent1 = new Intent(
													LogDietsSecondActivity.this,
													FinalizeLogActivity.class);
											intent1.putExtra("diet", diet);
											startActivityForResult(intent1, 8);
											dialog.dismiss();
											break;
										case 2:
											LogContainer dt2 = new LogContainer(
													namet, Val3t);

											ldBDD = new LogBDD(
													getApplicationContext());
											ldBDD.open();
											// dBDD.removeAllArticles();
											ldBDD.insertTop(dt2);
											ldBDD.close();
											Intent intent2 = new Intent(
													LogDietsSecondActivity.this,
													FinalizeLogActivity.class);
											intent2.putExtra("diet", diet);
											startActivityForResult(intent2, 8);
											dialog.dismiss();

											break;
										case 3:
											LogContainer dt3 = new LogContainer(
													namet, Val4t);

											ldBDD = new LogBDD(
													getApplicationContext());
											ldBDD.open();
											// dBDD.removeAllArticles();
											ldBDD.insertTop(dt3);
											ldBDD.close();
											Intent intent3 = new Intent(
													LogDietsSecondActivity.this,
													FinalizeLogActivity.class);
											intent3.putExtra("diet", diet);
											startActivityForResult(intent3, 8);
											dialog.dismiss();
											break;

										}

									}
								});
							}
						}

					}

				}

			}
		});
		search.addTextChangedListener(new TextWatcher() {

			@SuppressLint("DefaultLocale")
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				l1.invalidateViews();
				int textlength = s.length();
				tempArrayList = new ArrayList<LogDietsSecondActivity.ListViewItem>();
				for (ListViewItem c : items) {
					if (textlength <= c.Title.length()) {
						if (c.Title.toLowerCase().contains(
								s.toString().toLowerCase())) {
							tempArrayList.add(c);
						}
					}
				}
				adapter = new CustomAdapterLogSecond(getApplicationContext(),
						tempArrayList);

				l1.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				l1.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						namet = tempArrayList.get(position).Title;

						for (Nutrition n : l) {
							if (namet.equals(n.getName_N())) {
								Id_Nt = n.getId_N();

								Cat_Nt = n.getCategory_N();
								for (Details d : l2) {

									if (d.getId_NC().equals(Id_Nt)) {

										Part1t = d.getPart1();
										Part2t = d.getPart2();
										Part3t = d.getPart3();
										Part4t = d.getPart4();
										Val1t = d.getVal1();
										Val2t = d.getVal2();
										Val3t = d.getVal3();
										Val4t = d.getVal4();
										final Dialog dialog = new Dialog(
												LogDietsSecondActivity.this);
										// Include dialog.xml file
										dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
										dialog.setContentView(R.layout.dialog1);
										// Set dialog title
										listcal = (ListView) dialog
												.findViewById(R.id.listc);
										TextView namec = (TextView) dialog
												.findViewById(R.id.titlec);
										TextView typec = (TextView) dialog
												.findViewById(R.id.typec);
										namec.setText(namet);
										typec.setText(Cat_Nt);
										items2 = new ArrayList<LogDietsSecondActivity.ListViewItem2>();

										// set values for custom dialog
										// components -
										// text, image and button
										items2.add(new ListViewItem2() {
											{
												text1 = Part1t;
												text2 = Val1t;

											}
										});
										items2.add(new ListViewItem2() {
											{
												text1 = Part2t;
												text2 = Val2t;

											}
										});
										items2.add(new ListViewItem2() {
											{
												text1 = Part3t;
												text2 = Val3t;

											}
										});
										items2.add(new ListViewItem2() {
											{
												text1 = Part4t;
												text2 = Val4t;

											}
										});
										adapter2 = new CustomAdapterLogSecond2(
												getApplicationContext(), items2);

										listcal.setAdapter(adapter2);

										dialog.show();
										ButtonFlat declineButton = (ButtonFlat) dialog
												.findViewById(R.id.canc);
										// if decline button is clicked, close
										// the
										// custom dialog
										declineButton
												.setOnClickListener(new OnClickListener() {
													@Override
													public void onClick(View v) {
														// Close dialog
														dialog.dismiss();
													}
												});
										listcal.setOnItemClickListener(new OnItemClickListener() {

											@Override
											public void onItemClick(
													AdapterView<?> parent,
													View view, int position,
													long id) {
												// TODO Auto-generated method
												// stub

												switch (position) {
												case 0:
													LogContainer dt = new LogContainer(
															namet, Val1t);

													ldBDD = new LogBDD(
															getApplicationContext());
													ldBDD.open();
													// dBDD.removeAllArticles();
													ldBDD.insertTop(dt);
													ldBDD.close();
													Intent intent = new Intent(
															LogDietsSecondActivity.this,
															FinalizeLogActivity.class);
													intent.putExtra("diet",
															diet);
													startActivityForResult(
															intent, 8);
													dialog.dismiss();
													break;
												case 1:
													LogContainer dt1 = new LogContainer(
															namet, Val2t);

													ldBDD = new LogBDD(
															getApplicationContext());
													ldBDD.open();
													// dBDD.removeAllArticles();
													ldBDD.insertTop(dt1);
													ldBDD.close();
													Intent intent1 = new Intent(
															LogDietsSecondActivity.this,
															FinalizeLogActivity.class);
													intent1.putExtra("diet",
															diet);
													startActivityForResult(
															intent1, 8);
													dialog.dismiss();
													break;
												case 2:
													LogContainer dt2 = new LogContainer(
															namet, Val3t);

													ldBDD = new LogBDD(
															getApplicationContext());
													ldBDD.open();
													// dBDD.removeAllArticles();
													ldBDD.insertTop(dt2);
													ldBDD.close();
													Intent intent2 = new Intent(
															LogDietsSecondActivity.this,
															FinalizeLogActivity.class);
													intent2.putExtra("diet",
															diet);
													startActivityForResult(
															intent2, 8);
													dialog.dismiss();

													break;
												case 3:
													LogContainer dt3 = new LogContainer(
															namet, Val4t);

													ldBDD = new LogBDD(
															getApplicationContext());
													ldBDD.open();
													// dBDD.removeAllArticles();
													ldBDD.insertTop(dt3);
													ldBDD.close();
													Intent intent3 = new Intent(
															LogDietsSecondActivity.this,
															FinalizeLogActivity.class);
													intent3.putExtra("diet",
															diet);
													startActivityForResult(
															intent3, 8);
													dialog.dismiss();
													break;

												}

											}
										});
									}

								}
							}
						}
					}
				});

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

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

		}
		return super.onOptionsItemSelected(item);

	}

	public class ListViewItem {
		public int ThumbnailResource;
		public String Title;

	}

	public class ListViewItem2 {

		public String text1;
		public String text2;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 8 && resultCode == RESULT_OK) {
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
