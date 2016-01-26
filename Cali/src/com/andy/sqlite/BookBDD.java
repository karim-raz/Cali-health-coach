package com.andy.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.entities.LogBooks;
import com.andy.entities.LogContainer;

public class BookBDD {

	private static final int VERSION_BDD = 1;
	private static final String NAME_BDD = "logbooks.db";

	private SQLiteDatabase bdd;

	private DBHelper dbHelper;

	public BookBDD(Context context) {
		super();
		dbHelper = new DBHelper(context, NAME_BDD, null, VERSION_BDD);
	}

	public void open() {
		// TODO Open Data Base
		bdd = dbHelper.getWritableDatabase();
	}

	public void close() {
		// TODO Close Data Base
		dbHelper.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}

	public long insertTop(LogBooks lg) {

		// TODO Add Article to data base
		ContentValues values = new ContentValues();

		
		values.put(DBHelper.DATE_BOOK, lg.getDate());
		values.put(DBHelper.VALUE_BOOK, lg.getWeight());

		bdd.insert(DBHelper.TABLE_BOOK, null, values);

		return 0;
	}

	public int removeAllArticles() { // TODO Remove all Table
		bdd.execSQL("DELETE FROM " + DBHelper.TABLE_BOOK);
		return 0;
	}

	public List<LogBooks> selectAll() {
		List<LogBooks> list = new ArrayList<LogBooks>();
		// TODO Get list of Article
		Cursor cursor = bdd.query(DBHelper.TABLE_BOOK, new String[] {

		 DBHelper.DATE_BOOK ,DBHelper.VALUE_BOOK}, null, null, null,
				null, null);

		if (cursor.moveToFirst()) {
			do {
				LogBooks p = new LogBooks(cursor.getString(0),
						cursor.getString(1));
				list.add(p);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
