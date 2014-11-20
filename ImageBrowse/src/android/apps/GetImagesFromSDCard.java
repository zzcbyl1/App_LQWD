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
		//影藏顶部程序名称 写在setContentView(R.layout.gallery_1);之前，不然报错
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.gallery_1);
		//影藏顶部电量等图标
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setContentView(R.layout.sdcard);
//		display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		initView();
		//显示标题栏的进度圈
		setProgressBarIndeterminateVisibility(true);
		loadImages();
	}

	private void initView() {
		sdcardImages = (GridView) findViewById(R.id.sdcard);
		//设置图片显示的列数
//		sdcardImages.setNumColumns(display.getWidth() / 95);
//		//By default, children are clipped to the padding of the ViewGroup
//		sdcardImages.setClipToPadding(false);
		
		sdcardImages.setNumColumns(3);
		sdcardImages.setClipToPadding(true);
		//设置监听
		sdcardImages.setOnItemClickListener(GetImagesFromSDCard.this);
		//构造函数  参数相当于（this）
		imageAdapter = new ImageAdapter(getApplicationContext());
		//为图片添加适配器
		sdcardImages.setAdapter(imageAdapter);
	}

	private void loadImages() {
		//得到横屏时临时存储的数据
		final Object data = getLastNonConfigurationInstance();
		if (data == null) {//如果没有数据则从新加载
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
			//把图片添加到适配器里面，以便调整图片的属性
			imageAdapter.addPhoto(image);
			imageAdapter.notifyDataSetChanged();
		}
	}
////当横屏时：得到竖屏时临时存储的数据
//	@Override
//	public Object onRetainNonConfigurationInstance() {
//		final GridView grid = sdcardImages;
//		final int count = grid.getChildCount();//这里是竖屏时已经加载的图片数量
//		final LoadedImage[] list = new LoadedImage[count];
//
//		for (int i = 0; i < count; i++) {
//			final ImageView v = (ImageView) grid.getChildAt(i);
//			//得到先前的位图
//			list[i] = new LoadedImage(
//					((BitmapDrawable) v.getDrawable()).getBitmap());
//		}
//		return list;
//	}
	/*读取sdcard上的图片*/
	class LoadImagesFromSDCard extends AsyncTask<Object, LoadedImage, Object> {

		@Override
		protected Object doInBackground(Object... params) {
			Bitmap bitmap = null;//old Bitmap (位图)
			Bitmap newBitmap = null;//new Bitmap
			Uri uri = null;
			//想要的返回值所在的列
			String[] projection = { MediaStore.Images.Thumbnails._ID};
			//图片信息存储在 android.provider.MediaStore.Images.Thumbnails数据库 
			//快速查询数据库中的图片对应存放路劲         
			Cursor cursor = managedQuery(
					MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
					projection, //List of columns to return ：想要他返回的列
					null, // Return all rows
					null,
					null);
			int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
			uriArray = new Uri[cursor.getCount()];//把图片路径放在数组中
			int i=0;
			while(cursor.moveToNext()&&i<cursor.getCount())
			{   //移到指定的位置，遍历数据库
				cursor.moveToPosition(i);
				uri = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,cursor.getInt(columnIndex)+"");
				uriArray[i]=uri;
				try {
					bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
					if (bitmap != null) {
						//将原来的位图转换成新的位图
						newBitmap = Bitmap.createScaledBitmap(bitmap,80, 80,true);
						bitmap.recycle();//释放内存
						if (newBitmap != null) {
							publishProgress(new LoadedImage(newBitmap));
						}
					}
				} catch (IOException e) {}
				i++;
			}
			cursor.close();//关闭数据库
			return null;
		}
		@Override
		public void onProgressUpdate(LoadedImage... value) {
			addImage(value);
		}
		//当加载完时停止标题栏的进度圈
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
		//把图片添加到数组
		public void addPhoto(LoadedImage photo) {
			photos.add(photo);
		}
		//得到图片数量
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
			imageView.setPadding(1, 1, 1, 1);//控制图片距离窗口的位置
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
	//图片点击监听
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		//把图片位置赋给静态变量imagePosition，方便后面调用
		imagePosition=position;
		//跳转到大图浏览
		Intent intent = new Intent();
		intent.setClass(GetImagesFromSDCard.this, Gallery1.class);
		startActivity(intent);
	}
}
