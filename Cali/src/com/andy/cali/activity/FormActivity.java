package com.andy.cali.activity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.andy.cali.R;
import com.andy.entities.LogBooks;
import com.andy.entities.User;
import com.andy.sqlite.BookBDD;
import com.andy.sqlite.UserBDD;
import com.gc.materialdesign.views.ButtonRectangle;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class FormActivity extends Activity implements OnClickListener {

	ButtonRectangle confirm;
	String name;
	EditText ageEdit;
	EditText weightEdit;
	EditText heightEdit;
	String age;
	Dialog dialog;
	String height;
	ProgressWheel progressWheel;
	String month;
	int daymonth;
	String today;
	String weight;
	String gender;
	String id;
	String email;
	UserBDD userBDD;
	RadioButton radiofemale;
	RadioButton radiomale;
	Bitmap image;
	String from;
	String url;
	Intent intentcon;
	String urlfinale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_layout);
		Calendar c = Calendar.getInstance();
		daymonth = c.get(Calendar.DAY_OF_MONTH);
		int thisMonth = c.get(Calendar.MONTH) + 1;
		switch (thisMonth) {
		case 1:
			month = "January";
			break;
		case 2:
			month = "February";
			break;
		case 3:
			month = "March";
			break;
		case 4:
			month = "April";
			break;
		case 5:
			month = "May";
			break;
		case 6:
			month = "June";
			break;
		case 7:
			month = "July";
			break;
		case 8:
			month = "August";
			break;
		case 9:
			month = "September";
			break;
		case 10:
			month = "October";
			break;
		case 11:
			month = "November";
			break;
		case 12:
			month = "December";
			break;

		}
		today = daymonth + "\n" + month;

		intentcon = getIntent();

		from = intentcon.getStringExtra("from");
		if (from.equals("facebook")) {
			name = intentcon.getStringExtra("name");
			id = intentcon.getStringExtra("id");
			urlfinale = "https://graph.facebook.com/" + id
					+ "/picture?type=large&redirect=true&width=600&height=600";
			email = intentcon.getStringExtra("email");
		}
		if (from.equals("google")) {
			name = intentcon.getStringExtra("name");
			url = intentcon.getStringExtra("url");
			id = intentcon.getStringExtra("id");
			email = intentcon.getStringExtra("email");
			String x = url.substring(0, url.length() - 2);
			x = x + "1500";

			urlfinale = x;

		}

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.statusbar_bg);
		getActionBar().setTitle("Complete Registration");
		getActionBar().setIcon(R.drawable.cursor_red);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1E88E5")));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		ageEdit = (EditText) findViewById(R.id.EditText02);
		heightEdit = (EditText) findViewById(R.id.editText1);
		weightEdit = (EditText) findViewById(R.id.EditText01);
		radiofemale = (RadioButton) findViewById(R.id.female);
		radiomale = (RadioButton) findViewById(R.id.male);
		confirm = (ButtonRectangle) findViewById(R.id.button1);

		confirm.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		age = ageEdit.getText().toString();

		weight = weightEdit.getText().toString();
		height = heightEdit.getText().toString();

		if (radiofemale.isChecked()) {

			gender = "female";
		}
		if (radiomale.isChecked()) {
			gender = "male";

		}

		if (!age.isEmpty() && !weight.isEmpty() && !height.isEmpty()) {

			// TODO ADD new Artcile
			// user us = new user(name, age, height, weight, gender);
			// articleBDD = new ArticleBDD(getApplicationContext());
			// articleBDD.open();
			// articleBDD.insertTop(ar);
			// Toast.makeText(getApplicationContext(), "Article Saved",
			// Toast.LENGTH_LONG).show();
			// articleBDD.close();

			new TheTask().execute();
		} else {
			Toast.makeText(getApplicationContext(), "Empty field",
					Toast.LENGTH_LONG).show();

		}

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

	class TheTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new Dialog(FormActivity.this);
			// Include dialog.xml file
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog2);
			// Set dialog title

			progressWheel = (ProgressWheel) dialog
					.findViewById(R.id.progressBar1225);
			ProgressWheel wheel = new ProgressWheel(getApplicationContext());
			wheel.setBarColor(Color.parseColor("#1E88E5"));
			progressWheel.setVisibility(View.VISIBLE);

			// to use a view inside the xml (i.e. a button)

			dialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				image = downloadBitmap(urlfinale);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		
			if (image != null) {
				userBDD = new UserBDD(getApplicationContext());

				userBDD.open();
				// userBDD.removeAllArticles();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				image.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray();
				User us = new User(id, name, age, height, weight, gender,
						email, byteArray);

				userBDD.insertTop(us);
				userBDD.close();
				BookBDD bk = new BookBDD(getApplicationContext());
				bk.open();
				LogBooks lg = new LogBooks(today, weight);
				// bk.removeAllArticles();
				bk.insertTop(lg);
				bk.close();
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {

					
						dialog.dismiss();
						Intent intent = new Intent(FormActivity.this,
								ContainerActivity.class);

						startActivity(intent);
					
						finish();		

					}
				}, 2000);
				

				

			}

		}
	}

	private Bitmap downloadBitmap(String url) {
		// initilize the default HTTP client object
		final DefaultHttpClient client = new DefaultHttpClient();

		// forming a HttoGet request
		final HttpGet getRequest = new HttpGet(url);
		try {

			HttpResponse response = client.execute(getRequest);

			// check 200 OK for success
			final int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;

			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					// getting contents from the stream
					inputStream = entity.getContent();

					// decoding stream data back into image Bitmap that android
					// understands
					image = BitmapFactory.decodeStream(inputStream);

				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// You Could provide a more explicit error message for IOException
			getRequest.abort();
			Log.e("ImageDownloader", "Something went wrong while"
					+ " retrieving bitmap from " + url + e.toString());
		}

		return image;

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
