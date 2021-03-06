package com.example.android.gallery;

import android.R.anim;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ViewSwitcher.ViewFactory;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	private Gallery myGallery;
	private ImageSwitcher iSwitcher;

	private static final int[] images = { R.drawable.tu111, R.drawable.tu222,
			R.drawable.tu333, R.drawable.tu444, R.drawable.tu555,
			R.drawable.tu666, R.drawable.tu777 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Gallery��ImageSwitherЧ��");
		myGallery = (Gallery) findViewById(R.id.myGallery);
		iSwitcher = (ImageSwitcher) findViewById(R.id.imageSwither);

		myGallery.setAdapter(new ImageAdapter(this));

		myGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				iSwitcher.setImageResource(images[position % images.length]);


			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		iSwitcher.setFactory(new ImageFactory(this));
	}

	private class ImageAdapter extends BaseAdapter {
		private Context context;

		public ImageAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public Object getItem(int position) {
			return images[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView iv = new ImageView(context);
			iv.setImageResource(images[position % images.length]);
			iv.setLayoutParams(new Gallery.LayoutParams(90, 90));
			iv.setAdjustViewBounds(true);
			return iv;
		}
	}

	private class ImageFactory implements ViewFactory {
		private Context context;

		public ImageFactory(Context context) {
			this.context = context;
		}

		@Override
		public View makeView() {
			ImageView iv = new ImageView(context);
			iv.setLayoutParams(new ImageSwitcher.LayoutParams(200, 200));
			return iv;
		}
	}
}
