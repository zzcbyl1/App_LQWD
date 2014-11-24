package com.example.android_contentmenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView myTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myTV = (TextView) findViewById(R.id.contextMenuTV);
		registerForContextMenu(myTV);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.setHeaderTitle("Context Title");
		menu.add(1, 1, 1, "Item1");
		menu.add(2, 2, 2, "Item2");
		menu.add(3, 3, 3, "Item3");

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG)
				.show();

		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onPrepareOptionsMenu(menu);
	}
}
