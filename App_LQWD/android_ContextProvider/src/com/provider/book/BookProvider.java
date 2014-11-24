package com.provider.book;

import java.util.HashMap;
import com.database.helper.DataBaseHelper;
import com.provider.book.BookProviderMeteData.BookTableMeteData;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class BookProvider extends ContentProvider {

	private static final String TAG = "BookProdvider";
	private DataBaseHelper mOpenHelper;
	private static final int INCOMING_BOOK_COLLECTION_URI_INDICATOR = 1;
	private static final int INCOMING_SINGLE_BOOK_URI_INDICATOR = 2;
	private static final UriMatcher sUriMatcher;
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(BookProviderMeteData.AUTHORITY, "books",
				INCOMING_BOOK_COLLECTION_URI_INDICATOR);
		sUriMatcher.addURI(BookProviderMeteData.AUTHORITY, "books/#",
				INCOMING_SINGLE_BOOK_URI_INDICATOR);
	}

	private static HashMap<String, String> sBooksProjectionMap;
	static {
		sBooksProjectionMap = new HashMap<String, String>();
		sBooksProjectionMap.put(BookTableMeteData._ID, BookTableMeteData._ID);
		sBooksProjectionMap.put(BookTableMeteData.BOOK_NAME,
				BookTableMeteData.BOOK_NAME);
		sBooksProjectionMap.put(BookTableMeteData.BOOK_ISBN,
				BookTableMeteData.BOOK_ISBN);
		sBooksProjectionMap.put(BookTableMeteData.BOOK_AUTHOR,
				BookTableMeteData.BOOK_AUTHOR);

		sBooksProjectionMap.put(BookTableMeteData.CREATED_DATE,
				BookTableMeteData.CREATED_DATE);
		sBooksProjectionMap.put(BookTableMeteData.MODIFIED_DATE,
				BookTableMeteData.MODIFIED_DATE);
	}

	@Override
	public boolean onCreate() {
		Log.i(TAG, "Provider create");
		mOpenHelper = new DataBaseHelper(getContext());
		return true;
	}

	/**
	 * ²éÑ¯
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (sUriMatcher.match(uri)) {
		case INCOMING_BOOK_COLLECTION_URI_INDICATOR:
			qb.setTables(BookTableMeteData.TABLE_NAME);
			qb.setProjectionMap(sBooksProjectionMap);
			break;
		case INCOMING_SINGLE_BOOK_URI_INDICATOR:
			qb.setTables(BookTableMeteData.TABLE_NAME);
			qb.setProjectionMap(sBooksProjectionMap);
			qb.appendWhere(BookTableMeteData._ID + " = "
					+ uri.getPathSegments().get(1));
			break;
		default:
			throw new IllegalArgumentException("Unknown Uri " + uri.toString());
		}

		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = BookTableMeteData.DEFAULT_SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, orderBy);
		// int i = c.getCount();
		c.setNotificationUri(getContext().getContentResolver(), uri);

		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case INCOMING_BOOK_COLLECTION_URI_INDICATOR:
			return BookTableMeteData.CONTENT_TYPE;
		case INCOMING_SINGLE_BOOK_URI_INDICATOR:
			return BookTableMeteData.CONTENT_ITEM_TYPE;

		default:
			throw new IllegalArgumentException("Unknown Uri " + uri.toString());
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (sUriMatcher.match(uri) != INCOMING_BOOK_COLLECTION_URI_INDICATOR) {
			throw new IllegalArgumentException("Unknown Uri " + uri.toString());
		}
		if (values == null)
			values = new ContentValues();

		Long now = Long.valueOf(System.currentTimeMillis());
		if (!values.containsKey(BookTableMeteData.CREATED_DATE)) {
			values.put(BookTableMeteData.CREATED_DATE, now);
		}
		if (!values.containsKey(BookTableMeteData.MODIFIED_DATE)) {
			values.put(BookTableMeteData.MODIFIED_DATE, now);
		}
		if (!values.containsKey(BookTableMeteData.BOOK_NAME)) {
			throw new SQLException("Book Name is needed " + uri.toString());
		}
		if (!values.containsKey(BookTableMeteData.BOOK_ISBN)) {
			values.put(BookTableMeteData.BOOK_ISBN, "Unknown");
		}
		if (!values.containsKey(BookTableMeteData.BOOK_AUTHOR)) {
			values.put(BookTableMeteData.BOOK_AUTHOR, "Unknown");
		}

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowid = db.insert(BookTableMeteData.TABLE_NAME, null, values);
		if (rowid > 0) {
			Uri insertedBookUri = ContentUris.withAppendedId(uri, rowid);
			getContext().getContentResolver().notifyChange(insertedBookUri,
					null);
			return insertedBookUri;
		}

		throw new SQLException("Failed to insert row " + uri.toString());
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count = 0;
		switch (sUriMatcher.match(uri)) {
		case INCOMING_BOOK_COLLECTION_URI_INDICATOR:
			count = db.delete(BookTableMeteData.TABLE_NAME, selection,
					selectionArgs);
			break;
		case INCOMING_SINGLE_BOOK_URI_INDICATOR:
			String rowid = uri.getPathSegments().get(1);
			count = db
					.delete(BookTableMeteData.TABLE_NAME, BookTableMeteData._ID
							+ " = " + rowid + " and " + selection,
							selectionArgs);
		default:
			throw new IllegalArgumentException("Unknown Uri " + uri.toString());
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count = 0;
		switch (sUriMatcher.match(uri)) {
		case INCOMING_BOOK_COLLECTION_URI_INDICATOR:
			count = db.update(BookTableMeteData.TABLE_NAME, values, selection,
					selectionArgs);
			break;
		case INCOMING_SINGLE_BOOK_URI_INDICATOR:
			String rowid = uri.getPathSegments().get(1);
			count = db.update(
					BookTableMeteData.TABLE_NAME,
					values,
					BookTableMeteData._ID
							+ " = "
							+ rowid
							+ (TextUtils.isEmpty(selection) ? "" : " and ("
									+ selection + ")"), selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown Uri " + uri.toString());
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
