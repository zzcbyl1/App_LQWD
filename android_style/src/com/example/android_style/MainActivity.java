package com.example.android_style;

import android.R.anim;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText myEdittText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myEdittText = (EditText) findViewById(R.id.myEditText);

		myEdittText.setText("Styling the EditText dynamdeasdee");

		Spannable spannable = (Spannable) myEdittText.getText();
		spannable.setSpan(new BackgroundColorSpan(Color.RED), 0, 8,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

		spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),
				0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);

		MenuItem item = menu.add(0, 1, 0, "item1");
		item.setIcon(R.drawable.ic_launcher);

		menu.add(0, 2, 2, "item2");
		menu.add(0, 3, 1, "item3");
		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 2) {
			Toast.makeText(MainActivity.this, item.getTitle(),
					Toast.LENGTH_LONG).show();
		}
		return false;
	};

	
	
	
}
