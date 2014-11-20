package com.zhangzc.bindservice.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText myEdit;
	private Button button1, button2, button3;
	private boolean isBind = false;
	private HelloBindService mybindService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myEdit = (EditText) findViewById(R.id.editText1);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);

		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent serviceIntent = new Intent(MainActivity.this,
						HelloBindService.class);
				bindService(serviceIntent, mConnection,
						Context.BIND_AUTO_CREATE);
				isBind = true;
			}
		});

		button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isBind) {
					isBind = false;
					unbindService(mConnection);
					mybindService = null;
				}
			}
		});

		button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mybindService == null) {
					myEdit.setText("请先绑定服务");
					return;
				}
				myEdit.setText(mybindService.getName());
			}
		});
	}

	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mybindService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mybindService = ((HelloBindService.LocalBinder) service)
					.getService();
		}
	};
}
