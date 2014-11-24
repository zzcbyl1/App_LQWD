package com.example.android_notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i("--Message--", "secondActivity");
		RelativeLayout layout = new RelativeLayout(this);

		TextView tView = new TextView(this);
		tView.setText("第二个Activity");
		layout.addView(tView);

		Button btn = new Button(this);
		btn.setText("跳转");
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 80, 0, 10);
		btn.setLayoutParams(params);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SecondActivity.this, MainActivity.class);
				startActivity(intent);

			}
		});
		layout.addView(btn);

		setContentView(layout);

	}
}
