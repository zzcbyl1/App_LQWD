package com.zzc.luqinwenda.app;

import com.zzc.luqinwenda.dal.DataService;
import com.zzc.luqinwenda.dal.UserJson2Object;
import com.zzc.luqinwenda.model.User;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private static final String TAG = "------Log------";
	private EditText loginET, pwdET;
	private Button btn_login, btn_regiest;

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
	}

	private OnClickListener listener = new OnClickListener() {

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
				Log.i(TAG, "1111");
				UserJson2Object userJ2O = new UserJson2Object(result.toString());
				Log.i(TAG, "222");
				String infoString = userJ2O.isLogin();
				Log.i(TAG, infoString);
				if (!infoString.isEmpty()) {
					Log.i(TAG, "3333");
					Toast.makeText(LoginActivity.this, infoString,
							Toast.LENGTH_LONG).show();
				}
				Log.i(TAG, "444");
				User user = userJ2O.JsonToObject();
				Log.i(TAG, "555");
				SharedPreferences pre = getPreferences(MODE_PRIVATE);
				Editor editor = pre.edit();
				editor.putLong("uid", user.uid);
				editor.putString("uname", user.uname);
				editor.commit();

				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);

			}
			super.onPostExecute(result);
		}
	}

}
