package com.andy.adapter.iflater;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.SeekBar;
import android.widget.TextView;

import com.andy.adapter.BaseInflaterAdapter;
import com.andy.adapter.CardItemData;
import com.andy.adapter.IAdapterViewInflater;
import com.andy.cali.R;
import com.andy.utils.MyAnimatableView;

public class CardInflater2 implements IAdapterViewInflater<CardItemData> {
	TextView m_tv;
	MyAnimatableView m_atv;
	CardItemData item;
	BaseInflaterAdapter<CardItemData> adaptera;
	SeekBar bar1, bar2;
	TextView min, max, min1, max1;

	@Override
	public View inflate(final BaseInflaterAdapter<CardItemData> adapter,
			final int pos, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.list_item_card, parent,
					false);
		
			holder = new ViewHolder(convertView);
			if (pos == 2) {

				TextView m_text2;
				TextView min, max;
				min = (TextView) convertView.findViewById(R.id.min);
				max = (TextView) convertView.findViewById(R.id.maxl);
				min.setVisibility(View.INVISIBLE);
				max.setVisibility(View.INVISIBLE);
//				m_text2 = (TextView) convertView.findViewById(R.id.text2);
//				m_text2.setTextColor(Color.parseColor("#95a5a6"));

			}
			if (pos == 3) {

				TextView m_text2;
				TextView min, max;
				min = (TextView) convertView.findViewById(R.id.min);
				max = (TextView) convertView.findViewById(R.id.maxl);
				
				min.setVisibility(View.INVISIBLE);
				max.setVisibility(View.INVISIBLE);
				m_text2 = (TextView) convertView.findViewById(R.id.text2);
				m_text2.setTextColor(Color.parseColor("#95a5a6"));

			}
			if (pos == 0) {

				min = (TextView) convertView.findViewById(R.id.min);
				max = (TextView) convertView.findViewById(R.id.maxl);
				bar1 = (SeekBar) convertView.findViewById(R.id.seekBar1);
				bar1.setVisibility(View.VISIBLE);

				bar1.setClickable(false);
				bar1.setFocusable(false);
				bar1.setEnabled(false);
				bar1.setMax(Integer.valueOf(adapter.getTItem(0).getMaxVal1()));
				bar1.setProgress(Integer.valueOf(adapter.getTItem(0).getVal1()));
				min.setEnabled(false);
				max.setEnabled(false);
				min.setText(adapter.getTItem(0).getVal1().toString()
						+" "+ convertView.getResources().getString(R.string.callogged));
				max.setText(adapter.getTItem(0).getMaxVal1().toString());
			}
			if (pos == 1) {
				TextView text2;
				text2=(TextView)convertView.findViewById(R.id.text3);
				text2.setVisibility(View.INVISIBLE);
				min1 = (TextView) convertView.findViewById(R.id.min);
				max1 = (TextView) convertView.findViewById(R.id.maxl);
				bar2 = (SeekBar) convertView.findViewById(R.id.seekBar1);
				bar2.setVisibility(View.VISIBLE);
				min1.setEnabled(false);
				max1.setEnabled(false);
				bar2.setClickable(false);
				bar2.setFocusable(false);
				bar2.setEnabled(false);
				bar2.setMax(Integer.valueOf(adapter.getTItem(1).getMaxVal1()));
				bar2.setProgress(Integer.valueOf(adapter.getTItem(1).getVal1()));
				min1.setText(adapter.getTItem(1).getVal1().toString()
						+ " "+convertView.getResources().getString(R.string.stepsd4));
				max1.setText(adapter.getTItem(1).getMaxVal1().toString());

			}

		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		adaptera = adapter;
		item = adapter.getTItem(pos);
		holder.updateDisplay(item);

		return convertView;
	}

	private class ViewHolder {
		private View m_rootView;
		private ImageView m_text1;
		private TextView m_text2;
		private TextView m_text3;

		public ViewHolder(View rootView) {
			m_rootView = rootView;
			m_text1 = (ImageView) m_rootView.findViewById(R.id.ch01);
			m_text2 = (TextView) m_rootView.findViewById(R.id.text2);
			m_text3 = (TextView) m_rootView.findViewById(R.id.text3);

			rootView.setTag(this);
		}

		public void updateDisplay(CardItemData item) {
			m_text1.setImageResource(item.getText1());
			m_text1.setScaleType(ScaleType.CENTER_INSIDE);
			m_text2.setText(item.getText2());
			m_text3.setText(item.getText3());

		}

	}

}
