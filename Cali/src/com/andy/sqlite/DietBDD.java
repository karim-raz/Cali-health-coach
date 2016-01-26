package com.andy.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.entities.Diet;

public class DietBDD {

	private static final int VERSION_BDD = 3;
	private static final String NAME_BDD = "diet.db";

	private SQLiteDatabase bdd;

	private DBHelper dbHelper;

	public DietBDD(Context context) {
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

	public long insertTop(Diet dt) {

		// TODO Add Article to data base
		ContentValues values = new ContentValues();

		values.put(DBHelper.MAX_DIET, dt.getMax());
		values.put(DBHelper.DEF_DIET, dt.getDefaul());

		bdd.insert(DBHelper.TABLE_DIET, null, values);

		return 0;
	}

	public int removeAllArticles() { // TODO Remove all Table
		bdd.execSQL("DELETE FROM " + DBHelper.TABLE_DIET);
		return 0;
	}

	public List<Diet> selectAll() {
		List<Diet> list = new ArrayList<Diet>();
		// TODO Get list of Article
		Cursor cursor = bdd.query(DBHelper.TABLE_DIET, new String[] {

		DBHelper.MAX_DIET,DBHelper.DEF_DIET}, null, null, null,
				null, null);

		if (cursor.moveToFirst()) {
			do {
				Diet p = new Diet(cursor.getString(0),cursor.getString(1));
				list.add(p);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
