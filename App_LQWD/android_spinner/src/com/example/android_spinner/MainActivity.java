package com.example.android_spinner;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Spinner myddl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myddl = (Spinner) findViewById(R.id.myDropdownlist);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.ddlValue, android.R.layout.simple_list_item_1);

		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

		myddl.setAdapter(adapter);

		myddl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				Log.i("--tag--", "选中：" + ((TextView) view).getText() + " 位置："
						+ position + "ID：" + id);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Log.i("--tag--", "什么都没选");
			}
		});

	}
}
