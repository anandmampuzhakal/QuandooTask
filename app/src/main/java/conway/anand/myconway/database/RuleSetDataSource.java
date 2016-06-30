/**
 * Copyright (C) 2016-2017 Anand M Joseph.
 */

package conway.anand.myconway.database;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import conway.anand.myconway.app.AppConstants;

/**
 * @author anandmjoseph.
 * @date 28/06/16
 */

public class RuleSetDataSource {
	private SQLiteDatabase	database;
	private DatabaseHelper	dbHelper;
	private String[]		allColums	= { DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_N0, DatabaseHelper.COLUMN_N1, DatabaseHelper.COLUMN_N2, DatabaseHelper.COLUMN_N3, DatabaseHelper.COLUMN_N4, DatabaseHelper.COLUMN_N5, DatabaseHelper.COLUMN_N6, DatabaseHelper.COLUMN_N7, DatabaseHelper.COLUMN_N8 };

	public RuleSetDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void createRuleset(RuleSetConway ruleset) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_NAME, ruleset.getName());
		int[] rules = ruleset.getRuleSet();
		values.put(DatabaseHelper.COLUMN_N0, rules[0]);
		values.put(DatabaseHelper.COLUMN_N1, rules[1]);
		values.put(DatabaseHelper.COLUMN_N2, rules[2]);
		values.put(DatabaseHelper.COLUMN_N3, rules[3]);
		values.put(DatabaseHelper.COLUMN_N4, rules[4]);
		values.put(DatabaseHelper.COLUMN_N5, rules[5]);
		values.put(DatabaseHelper.COLUMN_N6, rules[6]);
		values.put(DatabaseHelper.COLUMN_N7, rules[7]);
		values.put(DatabaseHelper.COLUMN_N8, rules[8]);
		long id = database.insert(DatabaseHelper.TABLE_RULES, null, values);

		if (id == -1) {
			Log.w(AppConstants.APP_NAME, "Could not insert core data into database!");
		}
	}

	public List<RuleSetConway> getAllRulesets() {
		List<RuleSetConway> ruleSetConways = new ArrayList<RuleSetConway>();
		Cursor cursor = database.query(DatabaseHelper.TABLE_RULES, allColums, null, null, null, null, null);

		// cursor will be in front of the first entry so we
		// need to move it to the first item
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			RuleSetConway ruleSetConway = cursorToRuleSet(cursor);
			ruleSetConways.add(ruleSetConway);
			cursor.moveToNext();
		}

		cursor.close();
		return ruleSetConways;
	}

	public void deleteRuleset(RuleSetConway ruleSetConway) {
		long id = ruleSetConway.getId();
		database.delete(DatabaseHelper.TABLE_RULES, DatabaseHelper.COLUMN_ID + " = " + id, null);
	}

	private RuleSetConway cursorToRuleSet(Cursor cursor) {
		RuleSetConway ruleSetConway = new RuleSetConway();
		ruleSetConway.setId(cursor.getLong(0));
		ruleSetConway.setName(cursor.getString(1));
		int[] rules = new int[9];

		for (int i = 0; i < rules.length; i++) {
			rules[i] = cursor.getInt(i + 2);
		}

		ruleSetConway.setRuleSet(rules);

		return ruleSetConway;
	}

}
