package com.example.android_broadcast_receiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final String MY_ACTION = "com.zzc.broadcastReceiver.MyReceiver";

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
				intent.setAction(MY_ACTION);
				intent.putExtra("msg", "发送广播消息");
				sendBroadcast(intent);

			}
		});
	}
}
