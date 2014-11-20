package com.zhangzc.bindservice.app;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class HelloBindService extends Service {

	private final IBinder mBinder = new LocalBinder();
	private String BOOKNAME = "android����";

	public class LocalBinder extends Binder {
		HelloBindService getService() {
			return HelloBindService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(this, "��Service", Toast.LENGTH_SHORT).show();
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Toast.makeText(this, "ȡ����Service", Toast.LENGTH_SHORT).show();
		return false;
	}

	public String getName() {
		return BOOKNAME;
	}

}
