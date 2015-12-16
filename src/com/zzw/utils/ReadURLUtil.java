package com.zzw.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadURLUtil {

	public static byte[] loadRawDataFromURL(String u) throws Exception {
		URL url = new URL(u);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		InputStream is = conn.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// »º´æ2KB
		final int BUFFER_SIZE = 2 * 1024;
		final int EOF = -1;

		int c;
		byte[] buf = new byte[BUFFER_SIZE];

		while (true) {
			c = bis.read(buf);
			if (c == EOF)
				break;

			baos.write(buf, 0, c);
		}

		conn.disconnect();
		is.close();

		byte[] data = baos.toByteArray();
		baos.flush();

		return data;
	}

}
