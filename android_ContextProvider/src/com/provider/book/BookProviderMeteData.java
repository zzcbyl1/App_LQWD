package com.provider.book;

import android.net.Uri;
import android.provider.BaseColumns;

public class BookProviderMeteData {

	public static final String AUTHORITY = "com.androidbook.provider.BookProvider";
	public static final String DATEBASE_NAME = "book.db";
	public static final int DATEBASE_VERSION = 1;
	public static final String BOOKS_TABLE_NAME = "books";

	// Book±íµÄÃèÊö
	public static final class BookTableMeteData implements BaseColumns {
		public static final String TABLE_NAME = "books";
		public static final Uri CONTENT_URI = Uri.parse("content://"
				+ AUTHORITY + "/books");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vud.androidbook.book";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.androidbook.book";
		public static final String DEFAULT_SORT_ORDER = "modified DESC";
		/**
		 * Ãû³Æ
		 */
		public static final String BOOK_NAME = "name";
		public static final String BOOK_ISBN = "isbn";
		public static final String BOOK_AUTHOR = "author";
		public static final String CREATED_DATE = "created";
		public static final String MODIFIED_DATE = "modified";
	}

}
