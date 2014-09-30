package com.game.zhangzc.guessgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	public static final int REQUESTCODE = 9999;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final SharedPreferences pre = getPreferences(MODE_PRIVATE);
		String userName = pre.getString("username", "");
		if (userName == "") {
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivityForResult(intent, REQUESTCODE);
		} else {
			
			
		}
	}
}
