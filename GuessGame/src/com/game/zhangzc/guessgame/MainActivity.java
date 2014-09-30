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
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final int REQUESTCODE = 9999;
	private static SharedPreferences pre;
	private static String userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		pre = getPreferences(MODE_PRIVATE);
		userName = pre.getString("username", "");
		if (userName == "") {
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivityForResult(intent, REQUESTCODE);
		} else {

		}

		Button mybtn = (Button) findViewById(R.id.button1);
		mybtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pre = getPreferences(MODE_PRIVATE);
				userName = pre.getString("username", "");
				Toast.makeText(MainActivity.this, userName, Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == REQUESTCODE) {

			// ¥Ê”√ªß√˚
			Editor editor = pre.edit();
			editor.putString("username", data.getExtras().getString("username"));
			editor.commit();
		}
	}

}
