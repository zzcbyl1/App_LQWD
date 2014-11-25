package com.example.cramerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn1, btn2, btn3;
	private TextView urlTV;
	private ImageView showImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);

		urlTV = (TextView) findViewById(R.id.showUrl);
		showImg = (ImageView) findViewById(R.id.showImg);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			
			break;
		case R.id.button2:

			break;
		case R.id.button3:

			break;

		default:
			break;
		}

	}
}
