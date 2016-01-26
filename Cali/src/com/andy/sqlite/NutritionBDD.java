package com.andy.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.entities.Nutrition;

public class NutritionBDD {

	private static final int VERSION_BDD = 3;
	private static final String NAME_BDD = "nutrition.db";

	private SQLiteDatabase bdd;

	private DBHelper dbHelper;

	public NutritionBDD(Context context) {
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

	public long insertTop(Nutrition nt) {

		// TODO Add Article to data base
		ContentValues values = new ContentValues();

		values.put(DBHelper.ID_NUT, nt.getId_N());
		values.put(DBHelper.NAME_NUT, nt.getName_N());
		values.put(DBHelper.CATEGORY_NUT, nt.getCategory_N());

		bdd.insert(DBHelper.TABLE_NUT, null, values);

		return 0;
	}

	public int removeAllArticles() { // TODO Remove all Table
		bdd.execSQL("DELETE FROM " + DBHelper.TABLE_NUT);
		return 0;
	}

	public List<Nutrition> selectAll() {
		List<Nutrition> list = new ArrayList<Nutrition>();
		// TODO Get list of Article
		Cursor cursor = bdd.query(DBHelper.TABLE_NUT, new String[] {

		DBHelper.ID_NUT, DBHelper.NAME_NUT ,DBHelper.CATEGORY_NUT}, null, null, null,
				null, null);

		if (cursor.moveToFirst()) {
			do {
				Nutrition p = new Nutrition(cursor.getString(0),
						cursor.getString(1),cursor.getString(2));
				list.add(p);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
