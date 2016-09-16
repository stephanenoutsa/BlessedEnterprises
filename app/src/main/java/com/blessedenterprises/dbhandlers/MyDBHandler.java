package com.blessedenterprises.dbhandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blessedenterprises.models.Code;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephnoutsa on 9/12/16.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    SQLiteDatabase db = null;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "be.db";
    public static final String TABLE_SESSION = "session";
    public static final String SESSION_COLUMN_ID = "_sid";
    public static final String SESSION_COLUMN_STATUS = "status";
    public static final String TABLE_CODES = "codes";
    public static final String CODES_COLUMN_ID = "_cid";
    public static final String CODES_COLUMN_CODE = "code";
    public static final String CODES_COLUMN_DATE = "date";
    public static final String TABLE_COUNT = "count";
    public static final String COUNT_COLUMN_ID = "_id";
    public static final String COUNT_COLUMN_LOG = "log";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        String session = "CREATE TABLE " + TABLE_SESSION + "(" +
                SESSION_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                SESSION_COLUMN_STATUS + " TEXT " +
                ")";
        db.execSQL(session);

        // Add placeholder values for Session table
        addSession("inactive");

        String codes = "CREATE TABLE " + TABLE_CODES + "(" +
                CODES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                CODES_COLUMN_CODE + " TEXT " + ", " +
                CODES_COLUMN_DATE + " TEXT " +
                ")";
        db.execSQL(codes);

        // Add placeholder values for Codes table
        addCode("null", "null");

        String count = "CREATE TABLE " + TABLE_COUNT + "(" +
                COUNT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                COUNT_COLUMN_LOG + " INTEGER " +
                ")";
        db.execSQL(count);

        // Add placeholder values for Count table
        addCount(0);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION + ";");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CODES + ";");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNT + ";");
        onCreate(db);
    }

    // Add a session in Session table
    public void addSession(String status) {
        ContentValues values = new ContentValues();
        values.put(SESSION_COLUMN_STATUS, status);
        if (db == null) {
            db = getWritableDatabase();
        }
        db.insert(TABLE_SESSION, null, values);
    }

    // Update session in Session table
    public void updateSession(String status) {
        ContentValues values = new ContentValues();
        values.put(SESSION_COLUMN_STATUS, status);
        if (db == null) {
            db = getWritableDatabase();
        }
        db.update(TABLE_SESSION, values, SESSION_COLUMN_ID + "=1", null);
    }

    // Get session status in Session table
    public String getSession() {
        String query = "SELECT * FROM " + TABLE_SESSION + ";";

        if (db == null)
            db = getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToLast();

        try {
            return c.getString(1);
        } finally {
            c.close();
        }
    }

    // Add a code in Codes table
    public void addCode(String code, String date) {
        ContentValues values = new ContentValues();
        values.put(CODES_COLUMN_CODE, code);
        values.put(CODES_COLUMN_DATE, date);
        if (db == null) {
            db = getWritableDatabase();
        }
        db.insert(TABLE_CODES, null, values);
    }

    // Check code in Codes table
    public boolean checkCode(String code) {
        String query = "SELECT * FROM " + TABLE_CODES + " WHERE " + CODES_COLUMN_CODE + "=\'" + code + "\';";
        if (db == null) {
            db = getReadableDatabase();
        }
        Cursor c = db.rawQuery(query, null);

        try {
            return (c != null);
        } finally {
            c.close();
        }
    }

    // Get all codes from Codes table
    public List<Code> getAllCodes() {
        List<Code> codes = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_CODES + ";";

        if (db == null) {
            db = getReadableDatabase();
        }
        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        // Loop through all rows and add each to list
        while (!c.isAfterLast()) {
            Code code = new Code();
            code.set_cid(Integer.parseInt(c.getString(0)));
            code.setCode(c.getString(1));
            code.setDate(c.getString(2));

            codes.add(code);

            c.moveToNext();
        }

        try {
            return codes;
        } finally {
            c.close();
        }
    }

    // Delete a particular code from Codes table
    public void deleteCode(int id) {
        String query = "DELETE FROM " + TABLE_CODES + " WHERE " + CODES_COLUMN_ID + " = " + id + ";";
        if (db == null)
            db = getWritableDatabase();
        try {
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete all codes from Code table
    public void deleteAllCodes() {
        String query = "DELETE FROM " + TABLE_CODES + ";";
        if (db == null)
            db = getWritableDatabase();
        try {
            db.execSQL(query);

            // Add placeholder values for Codes table
            addCode("null", "null");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add a count to Count table
    public void addCount(int count) {
        ContentValues values = new ContentValues();
        values.put(COUNT_COLUMN_LOG, count);
        if (db == null) {
            db = getWritableDatabase();
        }
        db.insert(TABLE_COUNT, null, values);
    }

    // Update count in Count table
    public void updateCount(int count) {
        ContentValues values = new ContentValues();
        values.put(COUNT_COLUMN_LOG, count);
        if (db == null) {
            db = getWritableDatabase();
        }
        db.update(TABLE_COUNT, values, COUNT_COLUMN_ID + "=1", null);
    }

    // Get the count from the Count table
    public int getCount() {
        String query = "SELECT * FROM " + TABLE_COUNT + ";";

        if (db == null)
            db = getReadableDatabase();

        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToLast();

        try {
            return Integer.parseInt(c.getString(1));
        } finally {
            c.close();
        }
    }

}
