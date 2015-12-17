package com.zzw.testrefresh;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.zzw.utils.Contants;
import com.zzw.utils.DaoRefush;
import com.zzw.utils.Loader;
import com.zzw.utils.Loader.OnUpdataProgressListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View.OnClickListener;

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
		mProgressDialog.setMessage("Мгдижа......");
		mProgressDialog.setProgressStyle(mProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.show();
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		image_url = params[0];
		addType = params[1];

		try {
			Loader mLoader = new Loader();
			mLoader.setOnUpdataProgress(new OnUpdataProgressListener() {

				@Override
				public void upDataProgress(int count, int total) {
					publishProgress(count, total);
				}
			});

			byte[] b = mLoader.loadRawDataFromURL(image_url);

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
		float count = values[0];
		float total = values[1];
		int f = (int) ((count / total) * 100);
		mProgressDialog.setProgress(f);
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