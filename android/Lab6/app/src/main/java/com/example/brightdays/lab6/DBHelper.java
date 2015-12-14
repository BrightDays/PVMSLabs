package com.example.brightdays.lab6;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Created by User-PC on 01.04.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "financeDB";
    public static final String TABLE_NAME = "myFinance";
    public static final String CAT_TABLE_NAME = "categories";

    public static final String TAG_INCOME = "income";
    public static final String TAG_EXPENCES = "expences";

    public static final String KEY_FIN_ID = "id";
    public static final String KEY_FIN_CAT = "category";
    public static final String KEY_FIN_DATE = "date";
    public static final String KEY_FIN_TYPE = "type";
    public static final String KEY_FIN_VALUE = "value";

    public static final String KEY_CAT_NAME = "cat_name";

    public static final int PERIOD_DAY = 0;
    public static final int PERIOD_WEEK = 1;
    public static final int PERIOD_MONTH = 2;
    public static final int PERIOD_YEAR = 3;
    public static final int PERIOD_CUSTOM = 4;

    private Context context;
    private int period;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        period = getPeriod();
    }

    private int getPeriod() {
        return ViewPagerFragment.getPeriod();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_FINANCE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_FIN_ID + " integer primary key autoincrement,"
                + KEY_FIN_CAT + " text,"
                + KEY_FIN_DATE + " text,"
                + KEY_FIN_TYPE + " text,"
                + KEY_FIN_VALUE + " integer"
                + ");";
        final String CREATE_CAT_TABLE = "CREATE TABLE " + CAT_TABLE_NAME + " ("
                + KEY_FIN_ID + " integer primary key autoincrement,"
                + KEY_CAT_NAME + " text"
                + ");";
        db.execSQL(CREATE_FINANCE_TABLE);
        db.execSQL(CREATE_CAT_TABLE);
    }

    public void addCategory(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_CAT_NAME, name);
        db.insert(CAT_TABLE_NAME, null, cv);
        db.close();
    }

    public void addFinance(Finance finance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_FIN_CAT, finance.getCategory());
        cv.put(KEY_FIN_DATE, finance.getDate());
        cv.put(KEY_FIN_TYPE, finance.getType());
        cv.put(KEY_FIN_VALUE, finance.getValue());
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    private String getPageName(String str) {
        String[] line = str.split("\\.");
        switch (period) {
            case PERIOD_DAY: {
                return str.split(" ")[0];
            }
            case PERIOD_WEEK: {

            }
            case PERIOD_MONTH: {
                return line[0] + "." + line[1];
            }
            case PERIOD_YEAR: {
                return line[0];
            }
            case PERIOD_CUSTOM: {

            }
        }
        return null;
    }

    public ArrayList<String> getCategories()
    {
        final String query = "SELECT * FROM " + CAT_TABLE_NAME;
        ArrayList<String> categories = new ArrayList<String>();
        Cursor c = this.getReadableDatabase().rawQuery(query, null);
        if (c != null)
        {
            if (c.moveToFirst()) {
                do {
                    categories.add(c.getString(c.getColumnIndex(KEY_CAT_NAME)));
                }
                while (c.moveToNext());
            }
        }
        return categories;
    }

    public ArrayList<String> parsePageName(String criteria) {
        final String getDateTables = "SELECT " + KEY_FIN_DATE + ", " + KEY_FIN_CAT
                + " FROM " + TABLE_NAME + " WHERE type = '" + criteria
                + "' ORDER BY " + KEY_FIN_DATE;
        LinkedHashSet<String> linkedSet = new LinkedHashSet<String>();
        Cursor c = this.getReadableDatabase().rawQuery(getDateTables, null);
        ArrayList<String> selectedCategories = ViewPagerFragment.getSelectedCategories();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    if (!selectedCategories.contains(c.getString(c.getColumnIndex(KEY_FIN_CAT))))
                        continue;
                    linkedSet.add(getPageName(c.getString(c.getColumnIndex(KEY_FIN_DATE))));
                }
                while (c.moveToNext());
            }
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String s : linkedSet)
            arrayList.add(s);
        return arrayList;
    }

    private String getQuery(String date, String criteria) {
        String[] str = date.split("\\.");
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + KEY_FIN_TYPE + " = " + "'" + criteria + "'"
                + " AND " + KEY_FIN_DATE + " LIKE '%";
        switch (period) {
            case PERIOD_DAY: {
                query += date + "%'";
                return query;
            }
            case PERIOD_MONTH: {
                query += str[0] + "." + str[1] + "%'";
                return query;
            }
            case PERIOD_YEAR: {
                query += str[0] + "%'";
                return query;
            }
        }
        return null;
    }

    public ArrayList<Finance> getAllDatabaseValues(String date, String criteria, int period) {
        String query = getQuery(date, criteria);
        Cursor c = this.getReadableDatabase().rawQuery(query, null);
        ArrayList<Finance> al = new ArrayList<Finance>();
        ArrayList<String> selectedCategories = ViewPagerFragment.getSelectedCategories();
        if (c != null) {
            if (c.moveToFirst()) {
                String str, category = null, type = null, dbDate = null;
                int value = 0;
                do {
                    if (!c.getString(c.getColumnIndex(KEY_FIN_TYPE)).equals(criteria))
                        continue;
                    if (!selectedCategories.contains(c.getString(c.getColumnIndex(KEY_FIN_CAT))))
                        continue;
                    value = c.getInt(c.getColumnIndex(KEY_FIN_VALUE));
                    category = c.getString(c.getColumnIndex(KEY_FIN_CAT));
                    dbDate = c.getString(c.getColumnIndex(KEY_FIN_DATE));
                    type = c.getString(c.getColumnIndex(KEY_FIN_TYPE));
                    al.add(new Finance(category, type, dbDate, value));
                } while (c.moveToNext());
            }
            c.close();
            return al;
        } else
            Log.d("DEBUG", "Cursor is null");
        c.close();
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
