package com.example.mydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomActivity extends Activity {

	private final long PAUSE_LEN = 1000;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_index);

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(WelcomActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		};

		handler.postDelayed(runnable, PAUSE_LEN);
	}
}
