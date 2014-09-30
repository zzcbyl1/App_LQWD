package com.example.android_contextprovider;

import com.provider.book.BookProviderMeteData.BookTableMeteData;

import android.R.integer;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void insert_book(View view) {
		ContentValues cv = new ContentValues();
		cv.put(BookTableMeteData.BOOK_NAME, "book1");
		cv.put(BookTableMeteData.BOOK_ISBN, "isbn1");
		cv.put(BookTableMeteData.BOOK_AUTHOR, "author1");

		ContentResolver cr = MainActivity.this.getContentResolver();
		Uri uri = BookTableMeteData.CONTENT_URI;
		Uri insertUri = cr.insert(uri, cv);

		Log.i("MainActivity", "Insert uri " + insertUri);
		// Toast.makeText(MainActivity.this, "test", Toast.LENGTH_LONG).show();
	}

	public void search_book(View view) {

		ContentResolver cr = MainActivity.this.getContentResolver();
		Uri uri = BookTableMeteData.CONTENT_URI;
		Cursor cursor = cr.query(uri, new String[] { BookTableMeteData._ID,
				BookTableMeteData.BOOK_NAME, BookTableMeteData.BOOK_ISBN,
				BookTableMeteData.BOOK_AUTHOR }, "", null,
				BookTableMeteData._ID + " ASC");
		String contentString = "";
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			contentString += cursor.getString(cursor
					.getColumnIndex(BookTableMeteData._ID))
					+ "¡¡"
					+ cursor.getString(cursor
							.getColumnIndex(BookTableMeteData.BOOK_NAME))
					+ "¡¡"
					+ cursor.getString(cursor
							.getColumnIndex(BookTableMeteData.BOOK_ISBN))
					+ "¡¡"
					+ cursor.getString(cursor
							.getColumnIndex(BookTableMeteData.BOOK_AUTHOR))
					+ "\n";
		}

		Log.i("MainActivity", contentString);
		Log.i("MainActivity", String.valueOf(cursor.getCount()));
	}

	public void update_book(View view) {
		ContentValues cv = new ContentValues();
		cv.put(BookTableMeteData.BOOK_NAME, "book2");
		cv.put(BookTableMeteData.BOOK_ISBN, "isbn2");

		ContentResolver cr = MainActivity.this.getContentResolver();
		Uri uri = BookTableMeteData.CONTENT_URI;
		int count = cr.update(uri, cv, "_ID=?", new String[] { "1" });
		Log.i("MainActivity", "update count " + count);
	}

	public void delete_book(View view) {

		ContentResolver cr = MainActivity.this.getContentResolver();
		Uri uri = BookTableMeteData.CONTENT_URI;
		int count = cr.delete(uri, "_ID=?", new String[] { "1" });

		Log.i("MainActivity", "delete count " + count);
	}

}
