package com.example.android_notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button mybtn1, mybtn2;
	private Notification n;
	private NotificationManager nm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mybtn1 = (Button) findViewById(R.id.button1);
		mybtn2 = (Button) findViewById(R.id.button2);
		String serviceString = NOTIFICATION_SERVICE;
		nm = (NotificationManager) getSystemService(serviceString);

		n = new Notification();
		n.icon = R.drawable.image1;
		n.tickerText = "Test Notification";
		n.when = System.currentTimeMillis();

		mybtn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						SecondActivity.class);
				PendingIntent pIntent = PendingIntent.getActivity(
						MainActivity.this, 0, intent, 1);
				n.setLatestEventInfo(MainActivity.this, "Title", "Content",
						pIntent);
				nm.notify(1, n);
			}
		});

		mybtn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nm.cancel(1);
			}
		});
	}
}
