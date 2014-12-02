package com.zzc.luqinwenda.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity implements OnClickListener {

	private TextView setAvatar;
	private ImageView avatarImg;
	private String[] changeImg = { "拍照", "从相册中选择" };
	private Uri photoUri;
	private String photoPath;
	private final int SELECT_TAKE_IMAGES = 1;
	private final int SELECT_PICK_IMAGES = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypage);

		setAvatar = (TextView) findViewById(R.id.setAvatar);
		avatarImg = (ImageView) findViewById(R.id.showAvatar);

		setAvatar.setOnClickListener(this);
		avatarImg.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// 设置头像
		if (v.getId() == R.id.setAvatar || v.getId() == R.id.showAvatar) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					MyActivity.this);
			builder.setTitle("选择图片来源");
			builder.setItems(changeImg, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which == 0) {
						// 拍照
						takePhoto();
					} else if (which == 1) {
						// 从相册中选择
						pickPhoto();
					}
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	// 拍照
	private void takePhoto() {
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			ContentValues cv = new ContentValues();
			photoUri = this.getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
			startActivityForResult(intent, SELECT_TAKE_IMAGES);
		} else {
			Toast.makeText(MyActivity.this, "内存卡不存在", Toast.LENGTH_LONG).show();
		}
	}

	// 选择照片
	private void pickPhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, SELECT_PICK_IMAGES);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			doPhoto(requestCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 获得图片,显示
	private void doPhoto(int requestCode, Intent data) {
		if (requestCode == SELECT_PICK_IMAGES) {
			if (data == null) {
				Toast.makeText(MyActivity.this, "选择文件出错", Toast.LENGTH_LONG)
						.show();
				return;
			}
			photoUri = data.getData();
			if (photoUri == null) {
				Toast.makeText(MyActivity.this, "选择文件出错", Toast.LENGTH_LONG)
						.show();
				return;
			}
		}

		String[] pro = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(photoUri, pro, null, null, null);
		if (cursor != null) {
			int columnIndex = cursor.getColumnIndexOrThrow(pro[0]);
			cursor.moveToFirst();
			photoPath = cursor.getString(columnIndex);
			cursor.close();
		}
		if (!photoPath.isEmpty()) {
			Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
			avatarImg.setImageBitmap(bitmap);

		} else {
			Toast.makeText(MyActivity.this, "选择文件出错", Toast.LENGTH_LONG).show();
			return;
		}
	}
}
