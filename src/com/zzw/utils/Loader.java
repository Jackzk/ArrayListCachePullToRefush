package com.zzw.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Loader {

	private OnUpdataProgressListener mOnUpdataProgressListener = null;

	public Loader() {

	}

	public interface OnUpdataProgressListener {
		public void upDataProgress(int count, int total);
	}

	public void setOnUpdataProgress(OnUpdataProgressListener listener) {
		mOnUpdataProgressListener = listener;
	}

	public byte[] loadRawDataFromURL(String u) throws Exception {
		URL url = new URL(u);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// 获取数据长度
		int total = conn.getContentLength();

		InputStream is = conn.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 缓存2KB
		final int BUFFER_SIZE = 2 * 1024;
		final int EOF = -1;

		int c;
		byte[] buf = new byte[BUFFER_SIZE];

		int count = 0;
		while (true) {
			c = bis.read(buf);
			if (c == EOF)
				break;

			count += c;

			if (mOnUpdataProgressListener != null)
				mOnUpdataProgressListener.upDataProgress(count, total);

			baos.write(buf, 0, c);
		}

		conn.disconnect();
		is.close();

		byte[] data = baos.toByteArray();
		baos.flush();

		return data;
	}
}
