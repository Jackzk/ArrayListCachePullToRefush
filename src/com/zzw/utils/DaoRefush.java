package com.zzw.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.zzw.testrefresh.RefushAdapter;

import android.graphics.Bitmap;
import android.widget.ListView;

public class DaoRefush {

	private ArrayList<HashMap<String, Object>> data;
	private RefushAdapter adapter;
	private ListView mListView;

	public DaoRefush(ArrayList<HashMap<String, Object>> data, RefushAdapter adapter, ListView mListView) {

		this.data = data;
		this.adapter = adapter;
		this.mListView = mListView;

	}

	public void addTop(Bitmap bmp) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(Contants.IMAGE_KEY, bmp);
		map.put(Contants.TEXT_KEY, "头部添加数据--" + data.size());
		data.add(0, map);
		adapter.notifyDataSetChanged();
	}

	public void addBottom(Bitmap bmp) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(Contants.IMAGE_KEY, bmp);
		map.put(Contants.TEXT_KEY, "尾部添加数据--" + data.size());
		data.add(map);
		adapter.notifyDataSetChanged();
		mListView.setSelection(ListView.FOCUS_DOWN);
	}
}
