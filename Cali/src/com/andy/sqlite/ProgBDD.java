package com.andy.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.entities.Program;

public class ProgBDD {

	private static final int VERSION_BDD = 2;
	private static final String NAME_BDD = "program.db";

	private SQLiteDatabase bdd;

	private DBHelper dbHelper;

	public ProgBDD(Context context) {
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

	public long insertTop(Program pr) {

		// TODO Add Article to data base
		ContentValues values = new ContentValues();

		values.put(DBHelper.WISHED_PROG, pr.getGoal());
		values.put(DBHelper.INTENSITY_PROG, pr.getIntensity());

		bdd.insert(DBHelper.TABLE_PROG, null, values);

		return 0;
	}

	public int removeAllArticles() { // TODO Remove all Table
		bdd.execSQL("DELETE FROM " + DBHelper.TABLE_PROG);
		return 0;
	}

	public List<Program> selectAll() {
		List<Program> list = new ArrayList<Program>();
		// TODO Get list of Article
		Cursor cursor = bdd.query(DBHelper.TABLE_PROG, new String[] {

		DBHelper.WISHED_PROG, DBHelper.INTENSITY_PROG }, null, null, null,
				null, null);

		if (cursor.moveToFirst()) {
			do {
				Program p = new Program(cursor.getString(0),
						cursor.getString(1));
				list.add(p);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
