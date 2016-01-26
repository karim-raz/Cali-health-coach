package com.andy.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.entities.Details;

public class DetailsBDD {

	private static final int VERSION_BDD = 1;
	private static final String NAME_BDD = "details.db";

	private SQLiteDatabase bdd;

	private DBHelper dbHelper;

	public DetailsBDD(Context context) {
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

	public long insertTop(Details dt) {

		// TODO Add Article to data base
		ContentValues values = new ContentValues();

		values.put(DBHelper.ID_DET, dt.getId_NC());
		values.put(DBHelper.P1_DET, dt.getPart1());
		values.put(DBHelper.V1_DET, dt.getVal1());
		values.put(DBHelper.P2_DET, dt.getPart2());
		values.put(DBHelper.V2_DET, dt.getVal2());
		values.put(DBHelper.P3_DET, dt.getPart3());
		values.put(DBHelper.V3_DET, dt.getVal3());
		values.put(DBHelper.P4_DET, dt.getPart4());
		values.put(DBHelper.V4_DET, dt.getVal4());

		bdd.insert(DBHelper.TABLE_DET, null, values);

		return 0;
	}

	public int removeAllArticles() { // TODO Remove all Table
		bdd.execSQL("DELETE FROM " + DBHelper.TABLE_DET);
		return 0;
	}

	public List<Details> selectAll() {
		List<Details> list = new ArrayList<Details>();
		// TODO Get list of Article
		Cursor cursor = bdd.query(DBHelper.TABLE_DET, new String[] {

		DBHelper.ID_DET, DBHelper.P1_DET, DBHelper.V1_DET, DBHelper.P2_DET,
				DBHelper.V2_DET, DBHelper.P3_DET, DBHelper.V3_DET,
				DBHelper.P4_DET, DBHelper.V4_DET }, null, null, null, null,
				null);

		if (cursor.moveToFirst()) {
			do {
				Details p = new Details(cursor.getString(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4),
						cursor.getString(5), cursor.getString(6),
						cursor.getString(7), cursor.getString(8));
				list.add(p);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
