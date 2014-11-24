/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.apps;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class Gallery1 extends Activity {
	private Context mContext;
	private Uri[] uri =GetImagesFromSDCard.uriArray;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Ӱ�ض����������� д��setContentView(R.layout.gallery_1);֮ǰ����Ȼ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.gallery_1);
		//Ӱ�ض���������ͼ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		Gallery g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new ImageAdapter(this));
		//������ʾ�ڼ���ͼƬ ������GetImagesFromSDCard�еľ�̬����
		g.setSelection(GetImagesFromSDCard.imagePosition);
	}
	
	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		public ImageAdapter(Context c) {
			mContext = c;
			/*
			 * ʹ����res/values/attrs.xml�е�<declare-styleable>���� ��Gallery����.
			 */
			TypedArray a = obtainStyledAttributes(R.styleable.Gallery1);
			/* ȡ��Gallery���Ե�Index id */
			mGalleryItemBackground = a.getResourceId(
					R.styleable.Gallery1_android_galleryItemBackground, 0);
			/* �ö����styleable�����ܹ�����ʹ�� */
			a.recycle();
		}

		/* ��д�ķ���getCount,����ͼƬ��Ŀ */
		public int getCount() {
			return uri.length;
		}

		/* ��д�ķ���getItemId,����ͼ�������id */

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		/* ��д�ķ���getView,����һView���� */
		public View getView(int position, View convertView, ViewGroup parent){
			ImageView view = new ImageView(Gallery1.this);
			//��������ͼƬ����Դ��ַ
			view.setImageURI(uri[position]);
			view.setScaleType(ImageView.ScaleType.FIT_XY);
			view.setLayoutParams(new Gallery.LayoutParams(240, 320));
			/* ����Gallery����ͼ */
			view.setBackgroundResource(mGalleryItemBackground);
			return view;
		}
		
	}
}
