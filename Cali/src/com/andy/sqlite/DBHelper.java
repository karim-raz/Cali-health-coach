package com.andy.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String TABLE_USER = "user";
	public static final String TABLE_PROG = "program";
	public static final String TABLE_NUT = "nutrition";
	public static final String TABLE_DET = "details";
	public static final String TABLE_LOG = "log";
	public static final String TABLE_DIET = "diet";
	public static final String TABLE_TIPS = "tips";
	public static final String TABLE_BOOK = "logbooks";
	// Nutrition
	public static final String NAME_NUT = "name_N";
	public static final String CATEGORY_NUT = "category_N";
	public static final String ID_NUT = "id_N";

	// Details
	public static final String ID_DET = "id_NC";
	public static final String P1_DET = "part1";
	public static final String P2_DET = "part2";
	public static final String P3_DET = "part3";
	public static final String P4_DET = "part4";
	public static final String V1_DET = "val1";
	public static final String V2_DET = "val2";
	public static final String V3_DET = "val3";
	public static final String V4_DET = "val4";

	// Program
	public static final String WISHED_PROG = "wished";
	public static final String INTENSITY_PROG = "intensity";
	// USER
	public static final String ID_USER = "id";
	public static final String NAME_USER = "name";
	public static final String GENDER_USER = "gender";
	public static final String AGE_USER = "age";
	public static final String WEIGHT_USER = "weight";
	public static final String HEIGHT_USER = "height";
	public static final String EMAIL_USER = "email";
	public static final String LOGO_USER = "logo";
	// log
	public static final String NAME_LOG = "name";
	public static final String VALUE_LOG = "value";
	// DIET
	public static final String MAX_DIET = "max";
	public static final String DEF_DIET = "defaul";
	// TIPS
	public static final String TITLE_1 = "title1";
	public static final String DESC_1 = "desc1";
	public static final String TITLE_2 = "title2";
	public static final String DESC_2 = "desc2";
	public static final String TITLE_3 = "title3";
	public static final String DESC_3 = "desc3";
	public static final String PHOTO_T = "photot";
	// LogBook
		public static final String DATE_BOOK = "date_book";
		public static final String VALUE_BOOK = "value_book";

	// Table Creation
	private static final String CREATE_PROG = "CREATE TABLE " + TABLE_PROG
			+ " (" + WISHED_PROG + " TEXT, " + INTENSITY_PROG + " TEXT);";
	private static final String CREATE_DIET = "CREATE TABLE " + TABLE_DIET
			+ " (" + MAX_DIET + " TEXT, " + DEF_DIET + " TEXT);";
	private static final String CREATE_BOOKS = "CREATE TABLE " + TABLE_BOOK
			+ " (" + DATE_BOOK + " TEXT, " + VALUE_BOOK + " TEXT);";
	private static final String CREATE_LOG = "CREATE TABLE " + TABLE_LOG + " ("
			+ NAME_LOG + " TEXT, " + VALUE_LOG + " TEXT);";
	private static final String CREATE_USER = "CREATE TABLE " + TABLE_USER
			+ " (" + ID_USER + " TEXT, " + NAME_USER + " TEXT, " + GENDER_USER
			+ " TEXT, " + AGE_USER + " TEXT, " + WEIGHT_USER + " TEXT,"
			+ HEIGHT_USER + " TEXT,"+EMAIL_USER+" TEXT," + LOGO_USER + " BLOB);";

	private static final String CREATE_NUT = "CREATE TABLE " + TABLE_NUT + " ("
			+ ID_NUT + " TEXT," + NAME_NUT + " TEXT," + CATEGORY_NUT
			+ " TEXT);";
	private static final String CREATE_DET = "CREATE TABLE " + TABLE_DET + " ("
			+ ID_DET + " TEXT, " + P1_DET + " TEXT, " + V1_DET + " TEXT, "
			+ P2_DET + " TEXT, " + V2_DET + " TEXT, " + P3_DET + " TEXT,"
			+ V3_DET + " TEXT, " + P4_DET + " TEXT, " + V4_DET + " TEXT);";
	private static final String CREATE_TIPS = "CREATE TABLE " + TABLE_TIPS
			+ " (" + TITLE_1 + " TEXT," + DESC_1 + " TEXT," + TITLE_2
			+ " TEXT," + DESC_2 + " TEXT," + TITLE_3
			+ " TEXT," + DESC_3 + " TEXT,"+ PHOTO_T + " BLOB);";

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Create Data Base
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_PROG);
		db.execSQL(CREATE_NUT);
		db.execSQL(CREATE_DET);
		db.execSQL(CREATE_LOG);
		db.execSQL(CREATE_DIET);
		db.execSQL(CREATE_TIPS);
		db.execSQL(CREATE_BOOKS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {

		// TODO Re-Create Data Base
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROG);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DET);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIET);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
		onCreate(db);
	}

}
