package com.example.android.gallery;

import javax.crypto.spec.IvParameterSpec;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewActivity extends Activity {

	private GridView myGV;
	private static final int[] images = { R.drawable.tu111, R.drawable.tu222,
			R.drawable.tu333, R.drawable.tu444, R.drawable.tu555,
			R.drawable.tu666, R.drawable.tu777 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);

		myGV = (GridView) findViewById(R.id.myGridView);

		myGV.setAdapter(new DataAdapter(this));

	}

	private static class DataAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;

		// private Bitmap[] imageBitMap = new Bitmap[images.length];
		// private Bitmap[] imageThumbs = new Bitmap[images.length];

		public DataAdapter(Context context) {
			this.context = context;
			inflater = LayoutInflater.from(context);

			/*
			 * for (int i = 0; i < images.length; i++) { imageBitMap[i] =
			 * BitmapFactory.decodeResource( context.getResources(), images[i]);
			 * imageThumbs[i] = Bitmap.createScaledBitmap(imageBitMap[i], 100,
			 * 100, false); }
			 */
		}

		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public Object getItem(int position) {
			return images[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.imageitem, null);
				viewHolder = new ViewHolder();
				viewHolder.iv = (ImageView) convertView
						.findViewById(R.id.myImageView);

				convertView.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			Log.i("--asass--", String.valueOf(position));
			// viewHolder.iv.setImageBitmap(imageThumbs[position]);
			viewHolder.iv.setImageResource(images[position]);

			return convertView;
		}

		static class ViewHolder {
			ImageView iv;

		}

	}
}
