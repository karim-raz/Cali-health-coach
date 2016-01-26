package com.andy.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.cali.R;
import com.andy.entities.LogBooks;
import com.andy.sqlite.BookBDD;
import com.andy.utils.MyMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;

public class LogBookFragment extends Fragment {
	List<LogBooks> l1;
	private LineChart[] mCharts = new LineChart[1];
	private Typeface mTf;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.activity_colored_lines,
				container, false);
		getActivity().getActionBar().setIcon(R.drawable.cursor_red);
		getActivity().getActionBar().setTitle(getResources().getString(R.string.cont3));
		mCharts[0] = (LineChart) root.findViewById(R.id.chart3);

		mTf = Typeface.createFromAsset(getActivity().getAssets(),
				"OpenSans-Bold.ttf");
		BookBDD bk = new BookBDD(getActivity());
		l1 = new ArrayList<LogBooks>();

		bk.open();
		// bk.removeAllArticles();
		l1 = bk.selectAll();

		bk.close();

		LineData data = getData(l1.size(), 200);

		for (int i = 0; i < mCharts.length; i++)
			// add some transparency to the color with "& 0x90FFFFFF"
			setupChart(mCharts[i], data, mColors[i % mColors.length]);

		return root;
	}

	private int[] mColors = new int[] {

	Color.rgb(255, 255, 255),

	};
	public String[] mMonths = new String[] { "Jan", "Feb", "Mar", "Apr", "May",
			"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	private void setupChart(LineChart chart, LineData data, int color) {

		// if enabled, the chart will always start at zero on the y-axis
		chart.setStartAtZero(false);

		// disable the drawing of values into the chart
		chart.setDrawYValues(false);

		chart.setDrawBorder(false);

		// no description text
		chart.setDescription("");
		chart.setNoDataTextDescription("You need to provide data for the chart.");

		// enable / disable grid lines
		chart.setDrawVerticalGrid(true);
		chart.setDrawHorizontalGrid(true);
		//
		// enable / disable grid background
		chart.setDrawGridBackground(false);
	

		// enable touch gestures
		chart.setTouchEnabled(true);

		// enable scaling and dragging
		chart.setDragEnabled(true);
	

		// if disabled, scaling can be done on x- and y-axis separately
		chart.setPinchZoom(false);
		MyMarkerView mv = new MyMarkerView(getActivity(),
				R.layout.custom_marker_view);

		// define an offset to change the original position of the marker
		// (optional)
		mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

		// set the marker to the chart
	chart.setMarkerView(mv);

		// enable/disable highlight indicators (the lines that indicate the
		// highlighted Entry)
		

		chart.setBackgroundColor(color);

		chart.setValueTypeface(mTf);
chart.setPadding(20, 0, 0, 0);
		// add data
		chart.setData(data);

		// get the legend (only possible after setting data)
		Legend l = chart.getLegend();

		// modify the legend ...
		// l.setPosition(LegendPosition.LEFT_OF_CHART);
		l.setForm(LegendForm.CIRCLE);
		l.setFormSize(10f);
		l.setTextColor(Color.parseColor("#1E88E5"));
		l.setTypeface(mTf);

		YLabels y = chart.getYLabels();
		y.setTextColor(Color.parseColor("#1E88E5"));
		y.setTypeface(mTf);
		y.setLabelCount(4);

		XLabels x = chart.getXLabels();
		x.setTextColor(Color.parseColor("#1E88E5"));
		x.setTypeface(mTf);

		// animate calls invalidate()...
		chart.animateX(100);
	}

	private LineData getData(int count, float range) {
		ArrayList<Entry> yVals = new ArrayList<Entry>();
		ArrayList<String> xVals = new ArrayList<String>();
		int j = 0;
		String x = l1.get(0).getDate();
		xVals.add(l1.get(0).getDate());
		yVals.add(new Entry(Float.valueOf(l1.get(0).getWeight()), 0));
		for (int i = 1; i < count; i++) {
			// if(l1.get(i).getDate().equals(l1.get(i-1).getDate())){
			// xVals.add("");
			// yVals.add(new Entry(Float.valueOf(l1.get(i).getWeight()), j));
			// }
			// else{

			if (x.equals(l1.get(i).getDate())) {

				xVals.add("");
				yVals.add(new Entry(Float.valueOf(l1.get(i).getWeight()), i));
			} else {
				x = l1.get(i).getDate();
				xVals.add(l1.get(i).getDate());
				yVals.add(new Entry(Float.valueOf(l1.get(i).getWeight()), i));
			}
			// }

		}

		// create a dataset and give it a type
		LineDataSet set1 = new LineDataSet(yVals, getResources().getString(R.string.cont3));
		// set1.setFillAlpha(110);
		// set1.setFillColor(Color.RED);

		set1.setLineWidth(4.0f);
		set1.setCircleSize(10f);
		
		set1.setColor(Color.parseColor("#1E88E5"));
		set1.setCircleColor(Color.parseColor("#1E88E5"));
		
		set1.setHighLightColor(Color.parseColor("#1E88E5"));

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		dataSets.add(set1); // add the datasets

		// create a data object with the datasets
		LineData data = new LineData(xVals, dataSets);

		return data;
	}

}
