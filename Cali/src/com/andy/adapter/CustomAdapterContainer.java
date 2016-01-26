package com.andy.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.cali.R;
import com.andy.cali.activity.ContainerActivity.ListViewItem;

public class CustomAdapterContainer extends BaseAdapter
// CHI,HOSNI,DJO
{

	LayoutInflater inflater;
	List<ListViewItem> items;

	public CustomAdapterContainer(Activity context, List<ListViewItem> items) {
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

		ListViewItem item = items.get(position);

		View vi = convertView;

		if (convertView == null)
			vi = inflater.inflate(R.layout.item_cont, null);

		ImageView imgView = (ImageView) vi.findViewById(R.id.imgThumbnail);
		TextView txtTitle = (TextView) vi.findViewById(R.id.txtTitle);

		imgView.setImageResource(item.ThumbnailResource);
		txtTitle.setText(item.Title);

		return vi;
	}
}