package com.database.helper;

import com.provider.book.BookProviderMeteData;
import com.provider.book.BookProviderMeteData.BookTableMeteData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "BookProdvider";

	public DataBaseHelper(Context context) {
		super(context, BookProviderMeteData.DATEBASE_NAME, null,
				BookProviderMeteData.DATEBASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "create datebase");
		db.execSQL("Create table " + BookTableMeteData.TABLE_NAME + "("
				+ BookTableMeteData._ID + " integer primary key,"
				+ BookTableMeteData.BOOK_NAME + " text,"
				+ BookTableMeteData.BOOK_ISBN + " text,"
				+ BookTableMeteData.BOOK_AUTHOR + " text,"
				+ BookTableMeteData.CREATED_DATE + " integer,"
				+ BookTableMeteData.MODIFIED_DATE + " integer);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "create update version");
		//测试使用,删除原数据
		db.execSQL("Drop table if exist" + BookTableMeteData.TABLE_NAME);
		onCreate(db);
	}

}
