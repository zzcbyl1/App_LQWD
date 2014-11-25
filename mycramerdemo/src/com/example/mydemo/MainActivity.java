package com.example.mydemo;

import java.util.HashMap;
import java.util.Map;

import com.example.mydemo.UploadUtil.OnUploadProcessListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
		OnUploadProcessListener {

	private Button btn1, btn2, btn3;
	private TextView urlTV, uploadImageResult;
	private ImageView showImg;
	private Uri photoUri;
	private String photoPath;
	private static String requestURL = "上传地址";

	// 使用照相机拍照获取图片
	public static final int SELECT_PIC_BY_TAKE_PHOTO = 1;
	// 使用相册中的图片
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

	// 去上传文件
	protected static final int TO_UPLOAD_FILE = 1;
	// 上传文件响应
	protected static final int UPLOAD_FILE_DONE = 2;
	// 选择文件
	public static final int TO_SELECT_PHOTO = 3;
	// 上传初始化
	private static final int UPLOAD_INIT_PROCESS = 4;
	// 上传中
	private static final int UPLOAD_IN_PROCESS = 5;
	private ProgressBar progressBar;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);

		urlTV = (TextView) findViewById(R.id.showUrl);
		uploadImageResult = (TextView) findViewById(R.id.uploadresult);
		showImg = (ImageView) findViewById(R.id.showImg);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);

		progressDialog = new ProgressDialog(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			pickPhoto();
			break;
		case R.id.button2:
			takePhoto();
			break;
		case R.id.button3:
			if (photoPath != null) {
				handler.sendEmptyMessage(TO_UPLOAD_FILE);
			} else {
				Toast.makeText(this, "上传的文件路径出错", Toast.LENGTH_LONG).show();
			}
			break;

		default:
			break;
		}

	}

	/**
	 * 选择照片
	 */
	private void pickPhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
	}

	/**
	 * 拍照
	 */
	private void takePhoto() {
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			ContentValues cv = new ContentValues();
			photoUri = this.getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
			startActivityForResult(intent, SELECT_PIC_BY_TAKE_PHOTO);

		} else {
			Toast.makeText(MainActivity.this, "内存卡不存在", Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			doPhoto(requestCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void doPhoto(int requestCode, Intent data) {
		if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
			if (data == null) {
				Toast.makeText(MainActivity.this, "选择图片文件出错", Toast.LENGTH_LONG)
						.show();
				return;
			}
			photoUri = data.getData();
			if (photoUri == null) {
				Toast.makeText(MainActivity.this, "选择图片文件出错", Toast.LENGTH_LONG)
						.show();
				return;
			}
		}

		String[] pojo = { MediaStore.Images.Media.DATA };
		Cursor c = managedQuery(photoUri, pojo, null, null, null);
		if (c != null) {
			int columnIndex = c.getColumnIndexOrThrow(pojo[0]);
			c.moveToFirst();
			photoPath = c.getString(columnIndex);
			c.close();
		}
		if (photoPath != null) {
			urlTV.setText(photoPath);
			Bitmap bmap = BitmapFactory.decodeFile(photoPath);
			showImg.setImageBitmap(bmap);

		} else {
			Toast.makeText(MainActivity.this, "选择文件出错", Toast.LENGTH_LONG)
					.show();
			return;
		}
	}

	/**
	 * 上传服务器响应回调
	 */

	public void onUploadDone(int responseCode, String message) {
		progressDialog.dismiss();
		Message msg = Message.obtain();
		msg.what = UPLOAD_FILE_DONE;
		msg.arg1 = responseCode;
		msg.obj = message;
		handler.sendMessage(msg);
	}

	private void toUploadFile() {
		uploadImageResult.setText("正在上传中...");
		progressDialog.setMessage("正在上传文件...");
		/*
		 * uploadImageResult.setText("正在上传中...");
		 * progressDialog.setMessage("正在请求服务器上传...");
		 * progressDialog.setTitle("信息");
		 * progressDialog.setIcon(drawable.ic_dialog_info);
		 * progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		 * progressDialog.setProgress(59);
		 * progressDialog.setIndeterminate(true);
		 */
		/*
		 * progressDialog.setButton("确认", new DialogInterface.OnClickListener(){
		 * public void onClick(DialogInterface dialog, int which) {
		 * dialog.cancel(); } });
		 */

		progressDialog.show();
		String fileKey = "img";
		UploadUtil uploadUtil = UploadUtil.getInstance();
		;
		uploadUtil.setOnUploadProcessListener(this); // 设置监听器监听上传状态

		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", "111");
		uploadUtil.uploadFile(photoPath, fileKey, requestURL, params);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TO_UPLOAD_FILE:
				toUploadFile();
				break;
			case UPLOAD_FILE_DONE:
				String result = "响应码：" + msg.arg1 + "\n响应信息：" + msg.obj
						+ "\n耗时：" + UploadUtil.getRequestTime() + "秒";
				uploadImageResult.setText(result);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

}
