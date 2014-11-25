package com.example.activityjumpanimdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		btn4 = (Button) findViewById(R.id.button3);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int version = Integer.valueOf(android.os.Build.VERSION.SDK);
		switch (v.getId()) {
		case R.id.button1:
			// 放大缩小跳转
			startActivity(new Intent(this, OtherActivity.class));
			if (version > 5) {
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			}
			break;
		/*case R.id.btn2:
			// 淡入淡出跳转
			startActivity(new Intent(this, OtherActivity.class));
			if (version > 5) {
				overridePendingTransition(R.anim.alphain, R.anim.alphaout);
			}
			break;
		case R.id.btn3:
			// 左向右跳转
			startActivity(new Intent(this, OtherActivity.class));
			if (version > 5) {
				overridePendingTransition(R.anim.move_in_right,
						R.anim.move_out_right);
			}
			break;
		case R.id.btn4:
			// 右向左跳转
			startActivity(new Intent(this, OtherActivity.class));
			if (version > 5) {
				overridePendingTransition(R.anim.move_in_left,
						R.anim.move_out_left);
			}
			break;*/
		}
		finish();
	}
}