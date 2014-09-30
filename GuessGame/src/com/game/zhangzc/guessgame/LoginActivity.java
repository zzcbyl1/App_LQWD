package com.game.zhangzc.guessgame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	private EditText UserNameET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		final SharedPreferences pre = getPreferences(MODE_PRIVATE);

		UserNameET = (EditText) findViewById(R.id.etUserName);
		Button beginbtn = (Button) findViewById(R.id.button1);
		beginbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (UserNameET.getText().toString().trim() == "") {
					UserNameET.setBackgroundColor(Color.RED);
					return;

				} else {
					// ���û���
					Editor editor = pre.edit();
					editor.putString("username", UserNameET.getText()
							.toString());
					editor.commit();

					setResult(MainActivity.REQUESTCODE);
					finish();
				}
			}
		});
	}

}