package com.zzw.testrefresh;

import java.util.ArrayList;
import java.util.HashMap;

import com.zzw.utils.Contants;
import com.zzw.utils.DaoRefush;
import com.zzw.utils.ReadURLUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

class RefushDataAsyncTask extends AsyncTask<String, Integer, Bitmap> {

	private ProgressDialog mProgressDialog;
	private String addType, image_url;
	private Context context;

	private DaoRefush mDaoRefush;
	private ArrayList<HashMap<String, Object>> cache;

	public RefushDataAsyncTask(Context context, ArrayList<HashMap<String, Object>> cache, DaoRefush mDaoRefush) {
		this.context = context;
		this.cache = cache;
		this.mDaoRefush = mDaoRefush;
	}

	@Override
	protected void onPreExecute() {
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage("加载中......");
		// mProgressDialog.setProgressStyle(mProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.show();
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		image_url = params[0];
		addType = params[1];
		// 进度条参数传递
		// for (int i = 0; i <= 100; i++) {
		// publishProgress(i);// 调用onProgressUpdate方法
		// SystemClock.sleep(30);
		// }

		try {

			byte[] b = ReadURLUtil.loadRawDataFromURL(image_url);
			Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(Contants.IMAGE_URL_CACHE_KEY, image_url);
			map.put(Contants.IMAGE_BITMAP_CACHE_KEY, bmp);

			cache.add(map);
			return bmp;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// 进度条参数设置
		// mProgressDialog.setProgress((Integer) values[0]);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		mProgressDialog.dismiss();

		if (addType.equals(Contants.ADD_TOP))
			mDaoRefush.addTop(result);
		if (addType.equals(Contants.ADD_BOTTON))
			mDaoRefush.addBottom(result);
	}

}