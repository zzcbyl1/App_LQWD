package com.example.android_notification2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Bundle bundle = intent.getExtras();
		Log.i("Receiver", bundle.getString("message"));

		Intent intent2 = new Intent();
		intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent2.setClass(context, DispalyActivity.class);

		context.startActivity(intent2);

	}

}
