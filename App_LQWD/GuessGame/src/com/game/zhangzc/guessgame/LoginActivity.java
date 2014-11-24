package com.game.zhangzc.guessgame;

import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText UserNameET;
	private static int index;
	public static LoginActivity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		instance = this;
		index = 0;
		UserNameET = (EditText) findViewById(R.id.etUserName);
		Button beginbtn = (Button) findViewById(R.id.button1);
		beginbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(UserNameET.getText().toString().trim())) {
					UserNameET
							.setBackgroundResource(R.drawable.textview_border_red);
					UserNameET.setTextColor(Color.BLACK);
					return;

				} else {
					Intent intent = new Intent();
					intent.putExtra("username", UserNameET.getText().toString());
					setResult(MainActivity.REQUESTCODE, intent);
					finish();
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			exit();
			return false;
		}
		return false;
	}

	private void exit() {
		if (index == 0) {
			Toast.makeText(LoginActivity.this, "再按一次返回键就可以退出啦",
					Toast.LENGTH_SHORT).show();
			index++;

			// 延时执行
			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					Message message = new Message();
					handler.sendMessage(message);
				}
			};
			Timer timer = new Timer();
			timer.schedule(task, 3000);

		} else {
			LoginActivity.this.finish();
			MainActivity.instance.finish();
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			index = 0;
		};
	};

}
