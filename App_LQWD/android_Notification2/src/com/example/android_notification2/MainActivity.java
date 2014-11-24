package com.example.android_notification2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final String ACTION_NAME = "com.zzc.notification.MyReceiver";
	private Button mybtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mybtn = (Button) findViewById(R.id.button1);

		mybtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("message", "·¢ËÍ¹ã²¥");
				intent.putExtras(bundle);
				intent.setAction(ACTION_NAME);
				sendBroadcast(intent);
				
			}
		});

	}
}
