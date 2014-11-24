package com.example.android_notification2;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DispalyActivity extends Activity {

	private Button cancelBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);

		cancelBtn = (Button) findViewById(R.id.button1);

		Notification n = new Notification();
		n.icon = R.drawable.icon1;
		n.tickerText = "广播";
		n.when = System.currentTimeMillis();

		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		n.setLatestEventInfo(this, "我的广播", "广播内容", pi);

		final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.notify(1, n);

		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				manager.cancel(1);
			}
		});
	}
}
