package com.andy.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.entities.User;

public class UserBDD {

	private static final int VERSION_BDD = 4;
	private static final String NAME_BDD = "user.db";

	private SQLiteDatabase bdd;

	private DBHelper dbHelper;

	public UserBDD(Context context) {
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

	public long insertTop(User partis) {

		// TODO Add Article to data base
		ContentValues values = new ContentValues();
		values.put(DBHelper.ID_USER, partis.getId());
		values.put(DBHelper.NAME_USER, partis.getName());
		values.put(DBHelper.AGE_USER, partis.getAge());
		values.put(DBHelper.GENDER_USER, partis.getGender());
		values.put(DBHelper.WEIGHT_USER, partis.getWeight());
		values.put(DBHelper.HEIGHT_USER, partis.getHeight());
		values.put(DBHelper.EMAIL_USER, partis.getEmail());
		values.put(DBHelper.LOGO_USER, partis.getLogo());
		bdd.insert(DBHelper.TABLE_USER, null, values);

		return 0;
	}

	public int removeAllArticles() { // TODO Remove all Table
		bdd.execSQL("DELETE FROM " + DBHelper.TABLE_USER);
		return 0;
	}

	public int removeArticle(int index) { // TODO Remove one Article
		bdd.delete(DBHelper.TABLE_USER, DBHelper.ID_USER + " = ?",
				new String[] { index + "" });
		return 0;
	}

	public List<User> selectAll() {
		List<User> list = new ArrayList<User>();
		// TODO Get list of Article
		Cursor cursor = bdd.query(DBHelper.TABLE_USER,
				new String[] { DBHelper.ID_USER, DBHelper.NAME_USER,
						DBHelper.AGE_USER, DBHelper.HEIGHT_USER,
						DBHelper.WEIGHT_USER, DBHelper.GENDER_USER,
						dbHelper.EMAIL_USER, DBHelper.LOGO_USER }, null, null,
				null, null, null);

		if (cursor.moveToFirst()) {
			do {
				User us = new User(cursor.getString(0), cursor.getString(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getString(4), cursor.getString(5),
						cursor.getString(6), cursor.getBlob(7));
				list.add(us);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
