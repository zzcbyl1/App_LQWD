package com.example.activityjumpanimdemo;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OtherActivity extends Activity {

	private LayoutParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		TextView tView = new TextView(this);
		params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		tView.setLayoutParams(params);
		tView.setText("这是第二个Activity");

		layout.addView(tView);
		setContentView(layout);
	}
}
