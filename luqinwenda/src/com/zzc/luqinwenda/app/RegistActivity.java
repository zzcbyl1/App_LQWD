package com.zzc.luqinwenda.app;

import com.zzc.luqinwenda.dal.DataService;
import com.zzc.luqinwenda.dal.UserJson2Object;
import com.zzc.luqinwenda.model.User;

import android.R.integer;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RegistActivity extends Activity implements OnClickListener {

	public final static String TAG = "RegistActivity";
	private EditText loginET, pwdET, repwdET, unameET;
	private RadioGroup sexRG;
	private Button regBtn;
	private SharedPreferences pre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist);

		pre = getPreferences(MODE_PRIVATE);
		loginET = (EditText) findViewById(R.id.regusername);
		pwdET = (EditText) findViewById(R.id.regpwd);
		repwdET = (EditText) findViewById(R.id.regrepwd);
		unameET = (EditText) findViewById(R.id.reguname);
		sexRG = (RadioGroup) findViewById(R.id.sexgroup);
		regBtn = (Button) findViewById(R.id.registBtn);

		regBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registBtn:
			RadioButton radBtn = (RadioButton) RegistActivity.this
					.findViewById(sexRG.getCheckedRadioButtonId());

			String pwdStr = pwdET.getText().toString();
			String repwdStr = repwdET.getText().toString();
			if (!pwdStr.equals(repwdStr)) {
				Toast.makeText(RegistActivity.this, "两次输入的密码不一致",
						Toast.LENGTH_LONG).show();
				return;
			}

			new Regist().execute(loginET.getText().toString(), pwdStr, unameET
					.getText().toString(), radBtn.getText());
			break;
		default:
			break;
		}
	}

	class Regist extends AsyncTask<Object, Object, Object> {
		@Override
		protected Object doInBackground(Object... params) {
			DataService ds = new DataService();
			return ds.Regist(params[0].toString(), params[1].toString(),
					params[2].toString(), params[3].toString());
		}

		@Override
		protected void onPostExecute(Object result) {
			if (result != null) {
				//Log.i(RegistActivity.TAG, result.toString());
				UserJson2Object userJ2O = new UserJson2Object(result.toString());
				int status = userJ2O.getStatus();
				//Log.i(RegistActivity.TAG, String.valueOf(status));
				if (status == 0) {
					String infoString = userJ2O.getInfo("注册");
					Toast.makeText(RegistActivity.this, infoString,
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

		public void jumpMain() {
			Intent intent = new Intent();
			intent.setClass(RegistActivity.this, MainActivity.class);
			startActivity(intent);

			finish();
		}
	}

}
