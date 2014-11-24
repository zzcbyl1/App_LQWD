package com.zhangzc.actionbardemo.app;

import android.R.anim;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		actionBar = getActionBar();
		actionBar.show();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * super.onCreateOptionsMenu(menu); MenuItem add = menu.add(0, 0, 0,
		 * "add"); MenuItem del = menu.add(0, 0, 0, "del"); MenuItem save =
		 * menu.add(0, 0, 0, "save");
		 * add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		 * del.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		 * save.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		 */

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.item1:
			Toast.makeText(MainActivity.this, "save", Toast.LENGTH_LONG).show();
			break;
		case R.id.item2:
			Toast.makeText(MainActivity.this, "edit", Toast.LENGTH_LONG).show();
			break;
		case R.id.item3:
			Toast.makeText(MainActivity.this, "add", Toast.LENGTH_LONG).show();
			break;
		case R.id.item4:
			Toast.makeText(MainActivity.this, "del", Toast.LENGTH_LONG).show();
			break;
		case android.R.id.home:
			Toast.makeText(MainActivity.this, "home", Toast.LENGTH_LONG).show();
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
