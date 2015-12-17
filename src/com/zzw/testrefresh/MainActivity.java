package com.zzw.testrefresh;

import java.util.ArrayList;
import java.util.HashMap;

import com.zzw.utils.Contants;
import com.zzw.utils.DaoRefush;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class MainActivity extends Activity implements OnScrollListener {

	private ArrayList<HashMap<String, Object>> cache;

	private ArrayList<HashMap<String, Object>> data;
	private RefushAdapter adapter;
	private ListView mListView;

	// 测试数据
	private String image_url = "http://www.tukuchina.cn/images/front/v//1c/fc/tukuchina_2034727156.jpg";

	private RefushDataAsyncTask mRefushDataAsyncTask;
	public DaoRefush mDaoRefush;

	private int firstVisibleItem;
	private int visibleItemCount;
	private int totalItemCount;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;

		cache = new ArrayList<HashMap<String, Object>>();

		data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 20; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			map.put(Contants.IMAGE_KEY, bmp);
			map.put(Contants.TEXT_KEY, "原始数据--" + i);
			data.add(map);
		}

		mListView = (ListView) findViewById(R.id.listView);
		adapter = new RefushAdapter(this, R.layout.item, data);
		mListView.setAdapter(adapter);
		mListView.setOnScrollListener(this);

		mDaoRefush = new DaoRefush(data, adapter, mListView);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		if (OnScrollListener.SCROLL_STATE_IDLE == scrollState) {
			// 头部增加
			if (firstVisibleItem == 0) {
				loadData(image_url, Contants.ADD_TOP);
			}
			// 尾部增加
			if (firstVisibleItem + visibleItemCount == totalItemCount) {
				loadData(image_url, Contants.ADD_BOTTON);
			}
		}
	}

	public void loadData(String url, String addtype) {
		Log.d("检查url在不在缓存", url);
		for (int i = 0; i < cache.size(); i++) {
			HashMap<String, Object> map = cache.get(i);
			System.out.println(map);
			if (url.equals(map.get(Contants.IMAGE_URL_CACHE_KEY) + "")) {
				Log.d("存在缓存", url);
				Bitmap bmp = (Bitmap) map.get(Contants.IMAGE_BITMAP_CACHE_KEY);
				if (addtype.equals(Contants.ADD_TOP))
					mDaoRefush.addTop(bmp);
				if (addtype.equals(Contants.ADD_BOTTON))
					mDaoRefush.addBottom(bmp);
				return;
			}
		}
		Log.d("不存在缓存，开始加载", url);
		new RefushDataAsyncTask(context, cache, mDaoRefush).execute(url, addtype);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
		this.visibleItemCount = visibleItemCount;
		this.totalItemCount = totalItemCount;
	}

}
