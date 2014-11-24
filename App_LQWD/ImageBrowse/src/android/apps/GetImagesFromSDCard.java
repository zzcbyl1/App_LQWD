package android.apps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class GetImagesFromSDCard extends Activity implements
		OnItemClickListener {

	private GridView sdcardImages;
	private ImageAdapter imageAdapter;
//	private Display display;
	public static int imagePosition;
	public static Uri[] uriArray;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		//Ӱ�ض����������� д��setContentView(R.layout.gallery_1);֮ǰ����Ȼ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.gallery_1);
		//Ӱ�ض���������ͼ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setContentView(R.layout.sdcard);
//		display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		initView();
		//��ʾ�������Ľ���Ȧ
		setProgressBarIndeterminateVisibility(true);
		loadImages();
	}

	private void initView() {
		sdcardImages = (GridView) findViewById(R.id.sdcard);
		//����ͼƬ��ʾ������
//		sdcardImages.setNumColumns(display.getWidth() / 95);
//		//By default, children are clipped to the padding of the ViewGroup
//		sdcardImages.setClipToPadding(false);
		
		sdcardImages.setNumColumns(3);
		sdcardImages.setClipToPadding(true);
		//���ü���
		sdcardImages.setOnItemClickListener(GetImagesFromSDCard.this);
		//���캯��  �����൱�ڣ�this��
		imageAdapter = new ImageAdapter(getApplicationContext());
		//ΪͼƬ���������
		sdcardImages.setAdapter(imageAdapter);
	}

	private void loadImages() {
		//�õ�����ʱ��ʱ�洢������
		final Object data = getLastNonConfigurationInstance();
		if (data == null) {//���û����������¼���
			new LoadImagesFromSDCard().execute();
		}else {
			final LoadedImage[] photos = (LoadedImage[]) data;
			if (photos.length == 0) {
				new LoadImagesFromSDCard().execute();
			}
			for (LoadedImage photo : photos) {
				addImage(photo);
			}
		}
	}

	private void addImage(LoadedImage... value) {
		for (LoadedImage image : value) {
			//��ͼƬ��ӵ����������棬�Ա����ͼƬ������
			imageAdapter.addPhoto(image);
			imageAdapter.notifyDataSetChanged();
		}
	}
////������ʱ���õ�����ʱ��ʱ�洢������
//	@Override
//	public Object onRetainNonConfigurationInstance() {
//		final GridView grid = sdcardImages;
//		final int count = grid.getChildCount();//����������ʱ�Ѿ����ص�ͼƬ����
//		final LoadedImage[] list = new LoadedImage[count];
//
//		for (int i = 0; i < count; i++) {
//			final ImageView v = (ImageView) grid.getChildAt(i);
//			//�õ���ǰ��λͼ
//			list[i] = new LoadedImage(
//					((BitmapDrawable) v.getDrawable()).getBitmap());
//		}
//		return list;
//	}
	/*��ȡsdcard�ϵ�ͼƬ*/
	class LoadImagesFromSDCard extends AsyncTask<Object, LoadedImage, Object> {

		@Override
		protected Object doInBackground(Object... params) {
			Bitmap bitmap = null;//old Bitmap (λͼ)
			Bitmap newBitmap = null;//new Bitmap
			Uri uri = null;
			//��Ҫ�ķ���ֵ���ڵ���
			String[] projection = { MediaStore.Images.Thumbnails._ID};
			//ͼƬ��Ϣ�洢�� android.provider.MediaStore.Images.Thumbnails���ݿ� 
			//���ٲ�ѯ���ݿ��е�ͼƬ��Ӧ���·��         
			Cursor cursor = managedQuery(
					MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
					projection, //List of columns to return ����Ҫ�����ص���
					null, // Return all rows
					null,
					null);
			int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
			uriArray = new Uri[cursor.getCount()];//��ͼƬ·������������
			int i=0;
			while(cursor.moveToNext()&&i<cursor.getCount())
			{   //�Ƶ�ָ����λ�ã��������ݿ�
				cursor.moveToPosition(i);
				uri = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,cursor.getInt(columnIndex)+"");
				uriArray[i]=uri;
				try {
					bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
					if (bitmap != null) {
						//��ԭ����λͼת�����µ�λͼ
						newBitmap = Bitmap.createScaledBitmap(bitmap,80, 80,true);
						bitmap.recycle();//�ͷ��ڴ�
						if (newBitmap != null) {
							publishProgress(new LoadedImage(newBitmap));
						}
					}
				} catch (IOException e) {}
				i++;
			}
			cursor.close();//�ر����ݿ�
			return null;
		}
		@Override
		public void onProgressUpdate(LoadedImage... value) {
			addImage(value);
		}
		//��������ʱֹͣ�������Ľ���Ȧ
		@Override
		protected void onPostExecute(Object result) {
			setProgressBarIndeterminateVisibility(false);
		}
	}

	class ImageAdapter extends BaseAdapter {

		private Context mContext;
		private ArrayList<LoadedImage> photos = new ArrayList<LoadedImage>();

		public ImageAdapter(Context context) {
			mContext = context;
		}
		//��ͼƬ��ӵ�����
		public void addPhoto(LoadedImage photo) {
			photos.add(photo);
		}
		//�õ�ͼƬ����
		public int getCount() {
			return photos.size();
		}

		public Object getItem(int position) {
			return photos.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(mContext);
			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imageView.setPadding(1, 1, 1, 1);//����ͼƬ���봰�ڵ�λ��
			imageView.setImageBitmap(photos.get(position).getBitmap());
			return imageView;
		}
	}

	private static class LoadedImage {
		Bitmap mBitmap;

		LoadedImage(Bitmap bitmap) {
			mBitmap = bitmap;
		}

		public Bitmap getBitmap() {
			return mBitmap;
		}
	}
	//ͼƬ�������
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		//��ͼƬλ�ø�����̬����imagePosition������������
		imagePosition=position;
		//��ת����ͼ���
		Intent intent = new Intent();
		intent.setClass(GetImagesFromSDCard.this, Gallery1.class);
		startActivity(intent);
	}
}
