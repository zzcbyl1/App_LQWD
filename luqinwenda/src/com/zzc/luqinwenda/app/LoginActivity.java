package com.zzc.luqinwenda.app;

import com.zzc.luqinwenda.dal.DataService;
import com.zzc.luqinwenda.dal.UserJson2Object;
import com.zzc.luqinwenda.model.User;
import com.zzc.luqinwenda.util.ConfimDialog;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText loginET, pwdET;
	private Button btn_login, btn_regiest;
	private SharedPreferences pre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		loginET = (EditText) findViewById(R.id.loginText);
		pwdET = (EditText) findViewById(R.id.pwdText);
		btn_login = (Button) findViewById(R.id.loginBtn);
		btn_regiest = (Button) findViewById(R.id.registBtn);

		btn_login.setOnClickListener(listener);
		btn_regiest.setOnClickListener(listener);

		pre = getPreferences(MODE_PRIVATE);

		long uid = pre.getLong("uid", 0);
		if (uid > 0) {
			jumpMain();
		}
	}

	OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.loginBtn:
				new Login().execute(loginET.getText(), pwdET.getText());
				break;
			case R.id.registBtn:

				break;
			default:
				break;
			}

		}
	};

	class Login extends AsyncTask<Object, Object, Object> {
		@Override
		protected Object doInBackground(Object... params) {
			DataService ds = new DataService();
			return ds.Login(params[0].toString(), params[1].toString());
		}

		@Override
		protected void onPostExecute(Object result) {
			if (result != null) {
				UserJson2Object userJ2O = new UserJson2Object(result.toString());
				int status = userJ2O.getStatus();
				if (status == 0) {
					String infoString = userJ2O.getInfo("登录");
					Toast.makeText(LoginActivity.this, infoString,
							Toast.LENGTH_LONG).show();
					return;
				}
				User user = userJ2O.JsonToObject();

				Editor editor = pre.edit();
				editor.putLong("uid", user.uid);
				editor.putString("uname", user.uname);
				editor.commit();
				//Log.i(TAG, String.valueOf(user.uid));
				jumpMain();
			}
			super.onPostExecute(result);
		}
	}

	public void jumpMain() {
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, MainActivity.class);
		startActivity(intent);

		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		openCloseDialog(keyCode, LoginActivity.this);
		return false;
	}

	public static void openCloseDialog(int keyCode, Context context) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Dialog dialog = new ConfimDialog(context, "", "确定要退出吗?",
					R.style.MyDialog);
			dialog.show();
		}
	}

}
