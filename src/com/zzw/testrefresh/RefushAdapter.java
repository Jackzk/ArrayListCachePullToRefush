package com.zzw.testrefresh;

import java.util.ArrayList;
import java.util.HashMap;

import com.zzw.utils.Contants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RefushAdapter extends ArrayAdapter {

	private Context context;
	private int resource;
	private ArrayList<HashMap<String, Object>> data;
	private LayoutInflater inflater;

	public RefushAdapter(Context context, int resource, ArrayList<HashMap<String, Object>> data) {
		super(context, resource, data);
		this.context = context;
		this.resource = resource;
		this.data = data;

		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public HashMap<String, Object> getItem(int position) {
		return data.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		HashMap<String, Object> map = getItem(position);

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(resource, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView.findViewById(R.id.textView);
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Bitmap bmp = (Bitmap) map.get(Contants.IMAGE_KEY);

		holder.imageView.setImageBitmap(bmp);
		holder.textView.setText(map.get(Contants.TEXT_KEY) + "");

		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
		public ImageView imageView;
	}

}
