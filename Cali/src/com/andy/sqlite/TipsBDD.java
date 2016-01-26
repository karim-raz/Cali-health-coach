package com.andy.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.entities.Tips;

public class TipsBDD {

	private static final int VERSION_BDD = 3;
	private static final String NAME_BDD = "tips.db";

	private SQLiteDatabase bdd;

	private DBHelper dbHelper;

	public TipsBDD(Context context) {
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

	public long insertTop(Tips tps) {

		// TODO Add Article to data base
		ContentValues values = new ContentValues();

		values.put(DBHelper.TITLE_1, tps.getTitle1());
		values.put(DBHelper.DESC_1, tps.getDesc1());
		values.put(DBHelper.TITLE_2, tps.getTitle2());
		values.put(DBHelper.DESC_2, tps.getDesc2());
		values.put(DBHelper.TITLE_3, tps.getTitle3());
		values.put(DBHelper.DESC_3, tps.getDesc3());
		values.put(DBHelper.PHOTO_T, tps.getPhotot());
		bdd.insert(DBHelper.TABLE_TIPS, null, values);

		return 0;
	}

	public int removeAllArticles() { // TODO Remove all Table
		bdd.execSQL("DELETE FROM " + DBHelper.TABLE_TIPS);
		return 0;
	}

	public List<Tips> selectAll() {
		List<Tips> list = new ArrayList<Tips>();
		// TODO Get list of Article
		Cursor cursor = bdd.query(DBHelper.TABLE_TIPS, new String[] {

		DBHelper.TITLE_1, DBHelper.DESC_1, DBHelper.TITLE_2, DBHelper.DESC_2,
				DBHelper.TITLE_3, DBHelper.DESC_3, DBHelper.PHOTO_T }, null,
				null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				Tips p = new Tips(cursor.getString(0), cursor.getString(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getString(4), cursor.getString(5),
						cursor.getBlob(6));
				list.add(p);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
