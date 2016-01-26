package com.andy.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.andy.cali.R;
import com.andy.entities.LogBooks;
import com.andy.sqlite.BookBDD;

public class WeightInFragment extends Fragment {
	EditText weight;
	String month;
	int daymonth;
	String today;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.weight_in_layout, container,
				false);

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

		today = daymonth + "\n" + month;

		List<LogBooks> l1;
		BookBDD bk = new BookBDD(getActivity());
		l1 = new ArrayList<LogBooks>();

		bk.open();
		// bk.removeAllArticles();
		l1 = bk.selectAll();

		bk.close();

		getActivity().getActionBar().setIcon(R.drawable.cursor_red);

		getActivity().getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1E88E5")));
		getActivity().getActionBar().setTitle(
				getResources().getString(R.string.cont2));
		weight = (EditText) rootView.findViewById(R.id.editText13);

		weight.setHint(l1.get(l1.size() - 1).getWeight());
		TextView datte = (TextView) rootView.findViewById(R.id.datte);
		datte.setText(daymonth + " " + month);
		weight.setSelected(false);

		return rootView;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case android.R.id.home:
			// finish();

			break;
		case R.id.action_check:

			if (!weight.getText().toString().isEmpty()) {
				BookBDD bk = new BookBDD(getActivity());
				bk.open();
				LogBooks lg = new LogBooks(today, weight.getText().toString());
				// bk.removeAllArticles();
				bk.insertTop(lg);
				bk.close();

				getFragmentManager().beginTransaction()

				.replace(R.id.lol, new LogBookFragment()).commit();
			}
			break;
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Add your menu entries here
		inflater.inflate(R.menu.check, menu);

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
