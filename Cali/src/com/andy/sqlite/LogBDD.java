package com.andy.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.entities.LogContainer;

public class LogBDD {

	private static final int VERSION_BDD = 1;
	private static final String NAME_BDD = "logcontainer.db";

	private SQLiteDatabase bdd;

	private DBHelper dbHelper;

	public LogBDD(Context context) {
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

	public long insertTop(LogContainer lc) {

		// TODO Add Article to data base
		ContentValues values = new ContentValues();

		
		values.put(DBHelper.NAME_LOG, lc.getName());
		values.put(DBHelper.VALUE_LOG, lc.getValue());

		bdd.insert(DBHelper.TABLE_LOG, null, values);

		return 0;
	}

	public int removeAllArticles() { // TODO Remove all Table
		bdd.execSQL("DELETE FROM " + DBHelper.TABLE_LOG);
		return 0;
	}

	public List<LogContainer> selectAll() {
		List<LogContainer> list = new ArrayList<LogContainer>();
		// TODO Get list of Article
		Cursor cursor = bdd.query(DBHelper.TABLE_LOG, new String[] {

		 DBHelper.NAME_LOG ,DBHelper.VALUE_LOG}, null, null, null,
				null, null);

		if (cursor.moveToFirst()) {
			do {
				LogContainer p = new LogContainer(cursor.getString(0),
						cursor.getString(1));
				list.add(p);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
