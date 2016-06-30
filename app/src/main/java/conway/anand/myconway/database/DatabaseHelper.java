/**
 * Copyright (C) 2016-2017 Anand M Joseph.
 */
package conway.anand.myconway.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import conway.anand.myconway.app.AppConstants;

/**
 * Created by Anand M Joseph on 28/06/16.
 *
 * Database helper class
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String	TABLE_RULES			= "rules";
	public static final String	COLUMN_ID			= "_id";
	public static final String	COLUMN_NAME			= "name";
	public static final String	COLUMN_N0			= "n0";
	public static final String	COLUMN_N1			= "n1";
	public static final String	COLUMN_N2			= "n2";
	public static final String	COLUMN_N3			= "n3";
	public static final String	COLUMN_N4			= "n4";
	public static final String	COLUMN_N5			= "n5";
	public static final String	COLUMN_N6			= "n6";
	public static final String	COLUMN_N7			= "n7";
	public static final String	COLUMN_N8			= "n8";

	private static final String	DATABASE_NAME		= "rules.db";
	private static final int	DATABASE_VERSION	= 1;

	// sql statement to create database
	private static final String	DATABASE_CREATE		= "create table " + TABLE_RULES + "( " + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NAME + " text not null, " + COLUMN_N0 + " integer not null, " + COLUMN_N1 + " integer not null, " + COLUMN_N2 + " integer not null, " + COLUMN_N3 + " integer not null, " + COLUMN_N4 + " integer not null, " + COLUMN_N5 + " integer not null, " + COLUMN_N6 + " integer not null, " + COLUMN_N7 + " integer not null, " + COLUMN_N8 + " integer not null);";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);

		// Fill with core data
		ContentValues values = new ContentValues();

		// Conway
		values.put(DatabaseHelper.COLUMN_NAME, "Conway");
		values.put(DatabaseHelper.COLUMN_N0, AppConstants.DEATH_RULE);
		values.put(DatabaseHelper.COLUMN_N1, AppConstants.DEATH_RULE);
		values.put(DatabaseHelper.COLUMN_N2, AppConstants.UNDEFINED);
		values.put(DatabaseHelper.COLUMN_N3, AppConstants.BIRTH_RULE);
		values.put(DatabaseHelper.COLUMN_N4, AppConstants.DEATH_RULE);
		values.put(DatabaseHelper.COLUMN_N5, AppConstants.DEATH_RULE);
		values.put(DatabaseHelper.COLUMN_N6, AppConstants.DEATH_RULE);
		values.put(DatabaseHelper.COLUMN_N7, AppConstants.DEATH_RULE);
		values.put(DatabaseHelper.COLUMN_N8, AppConstants.DEATH_RULE);
		submitToDb(database, values);

		// AntiConway
		values.put(DatabaseHelper.COLUMN_NAME, "AntiConway");
		values.put(DatabaseHelper.COLUMN_N0, AppConstants.BIRTH_RULE);
		values.put(DatabaseHelper.COLUMN_N1, AppConstants.BIRTH_RULE);
		values.put(DatabaseHelper.COLUMN_N2, AppConstants.BIRTH_RULE);
		values.put(DatabaseHelper.COLUMN_N3, AppConstants.BIRTH_RULE);
		values.put(DatabaseHelper.COLUMN_N4, AppConstants.BIRTH_RULE);
		values.put(DatabaseHelper.COLUMN_N5, AppConstants.DEATH_RULE);
		values.put(DatabaseHelper.COLUMN_N6, AppConstants.UNDEFINED);
		values.put(DatabaseHelper.COLUMN_N7, AppConstants.BIRTH_RULE);
		values.put(DatabaseHelper.COLUMN_N8, AppConstants.BIRTH_RULE);
		submitToDb(database, values);

		// Shuffle
		values.put(DatabaseHelper.COLUMN_NAME, "Shuffle");
		values.put(DatabaseHelper.COLUMN_N0, AppConstants.DEATH_RULE);
		values.put(DatabaseHelper.COLUMN_N1, AppConstants.BIRTH_RULE);
		values.put(DatabaseHelper.COLUMN_N2, AppConstants.DEATH_RULE);
		values.put(DatabaseHelper.COLUMN_N3, AppConstants.BIRTH_RULE);
		values.put(DatabaseHelper.COLUMN_N4, AppConstants.DEATH_RULE);
		values.put(DatabaseHelper.COLUMN_N5, AppConstants.BIRTH_RULE);
		values.put(DatabaseHelper.COLUMN_N6, AppConstants.DEATH_RULE);
		values.put(DatabaseHelper.COLUMN_N7, AppConstants.BIRTH_RULE);
		values.put(DatabaseHelper.COLUMN_N8, AppConstants.DEATH_RULE);
		submitToDb(database, values);
	}

	private void submitToDb(SQLiteDatabase database, ContentValues values) {
		long id = database.insert(DatabaseHelper.TABLE_RULES, null, values);

		if (id == -1) {
			Log.w(AppConstants.APP_NAME, "Could not insert core data into database!");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int vOld, int vNew) {
		database.execSQL("DROP TABLE IF EXISTS" + TABLE_RULES);
		onCreate(database);
	}

}
