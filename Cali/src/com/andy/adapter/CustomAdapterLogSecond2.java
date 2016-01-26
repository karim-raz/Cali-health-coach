package com.andy.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andy.cali.R;
import com.andy.cali.activity.LogDietsSecondActivity.ListViewItem2;

public class CustomAdapterLogSecond2 extends BaseAdapter {
	

		LayoutInflater inflater;
		List<ListViewItem2> items;

		public CustomAdapterLogSecond2(Context context, List<ListViewItem2> items) {
			super();

			this.items = items;
			this.inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@SuppressLint("InflateParams")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			ListViewItem2 item = items.get(position);

			View vi = convertView;

			if (convertView == null)
				vi = inflater.inflate(R.layout.item_cont2, null);

			TextView txt1 = (TextView) vi.findViewById(R.id.txt1);
			TextView txt2 = (TextView) vi.findViewById(R.id.txt2);

			txt1.setText(item.text1);
			txt2.setText(item.text2+" Cal");
			
			
			return vi;
		}
	}