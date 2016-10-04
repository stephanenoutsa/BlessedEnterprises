package com.blessedenterprises.dbhandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blessedenterprises.models.Host;
import com.blessedenterprises.models.User;

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

    public static final String TABLE_USERS = "users";
    public static final String USERS_COLUMN_ID = "_uid";
    public static final String USERS_COLUMN_NAME = "name";
    public static final String USERS_COLUMN_DATE = "date";
    public static final String USERS_COLUMN_LOGIN = "login";
    public static final String USERS_COLUMN_LOGOUT = "logout";
    public static final String USERS_COLUMN_HOST = "host";
    public static final String USERS_COLUMN_LINE = "line";

    public static final String TABLE_COUNT = "count";
    public static final String COUNT_COLUMN_ID = "_cid";
    public static final String COUNT_COLUMN_LOG = "log";

    public static final String TABLE_HOST = "host";
    public static final String HOST_COLUMN_ID = "_hid";
    public static final String HOST_COLUMN_NAME = "name";
    public static final String HOST_COLUMN_LINE = "line";

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

        String users = "CREATE TABLE " + TABLE_USERS + "(" +
                USERS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                USERS_COLUMN_NAME + " TEXT " + ", " +
                USERS_COLUMN_DATE + " TEXT " + ", " +
                USERS_COLUMN_LOGIN + " TEXT " + ", " +
                USERS_COLUMN_LOGOUT + " TEXT " + ", " +
                USERS_COLUMN_HOST + " TEXT " + ", " +
                USERS_COLUMN_LINE + " TEXT " +
                ")";
        db.execSQL(users);

        // Add placeholder values for Users table
        addUser("null", "null", "null", "null", "null", "null");

        String count = "CREATE TABLE " + TABLE_COUNT + "(" +
                COUNT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                COUNT_COLUMN_LOG + " INTEGER " +
                ")";
        db.execSQL(count);

        // Add placeholder values for Count table
        addCount(0);

        String host = "CREATE TABLE " + TABLE_HOST + "(" +
                HOST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                HOST_COLUMN_NAME + " TEXT " + ", " +
                HOST_COLUMN_LINE + " TEXT " +
                ")";
        db.execSQL(host);

        // Add placeholder values for Host table
        addHost("null", "null");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION + ";");
        onCreate(db);*/
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS + ";");
        onCreate(db);
        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNT + ";");
        onCreate(db);*/
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOST + ";");
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

    // Add a user in Users table
    public void addUser(String name, String date, String login, String logout, String host, String line) {
        ContentValues values = new ContentValues();
        values.put(USERS_COLUMN_NAME, name);
        values.put(USERS_COLUMN_DATE, date);
        values.put(USERS_COLUMN_LOGIN, login);
        values.put(USERS_COLUMN_LOGOUT, logout);
        values.put(USERS_COLUMN_HOST, host);
        values.put(USERS_COLUMN_LINE, line);
        if (db == null) {
            db = getWritableDatabase();
        }
        db.insert(TABLE_USERS, null, values);
    }

    // Get last user from Users table
    public User getUser() {
        String query = "SELECT * FROM " + TABLE_USERS + ";";
        if (db == null) {
            db = getReadableDatabase();
        }
        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToLast();

        User user = new User();
        user.set_uid(Integer.parseInt(c.getString(0)));
        user.setName(c.getString(1));
        user.setDate(c.getString(2));
        user.setLoginTime(c.getString(3));
        user.setLogoutTime(c.getString(4));
        user.setHost(c.getString(5));
        user.setLine(c.getString(6));

        c.close();
        return user;
    }

    // Get all users from Users table
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_USERS + ";";

        if (db == null) {
            db = getReadableDatabase();
        }
        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        // Loop through all rows and add each to list
        while (!c.isAfterLast()) {
            User user = new User();
            user.set_uid(Integer.parseInt(c.getString(0)));
            user.setName(c.getString(1));
            user.setDate(c.getString(2));
            user.setLoginTime(c.getString(3));
            user.setLogoutTime(c.getString(4));
            user.setHost(c.getString(5));
            user.setLine(c.getString(6));

            users.add(user);

            c.moveToNext();
        }

        try {
            return users;
        } finally {
            c.close();
        }
    }

    // Update a user in the Users table
    public void updateUser(String logout) {
        User user = getUser();
        int id = user.get_uid();
        ContentValues values = new ContentValues();
        values.put(USERS_COLUMN_LOGOUT, logout);
        if (db == null) {
            db = getWritableDatabase();
        }
        db.update(TABLE_USERS, values, USERS_COLUMN_ID + "=" + id, null);
    }

    // Delete a particular user from Users table
    public void deleteUser(int id) {
        String query = "DELETE FROM " + TABLE_USERS + " WHERE " + USERS_COLUMN_ID + " = " + id + ";";
        if (db == null)
            db = getWritableDatabase();
        try {
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete all users from Users table
    public void deleteAllUsers() {
        String query = "DELETE FROM " + TABLE_USERS + ";";
        if (db == null)
            db = getWritableDatabase();
        try {
            db.execSQL(query);

            // Add placeholder values for Host table
            addUser("null", "null", "null", "null", "null", "null");
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

    // Add host to Host table
    public void addHost(String name, String line) {
        ContentValues values = new ContentValues();
        values.put(HOST_COLUMN_NAME, name);
        values.put(HOST_COLUMN_LINE, line);
        if (db == null) {
            db = getWritableDatabase();
        }
        db.insert(TABLE_HOST, null, values);
    }

    // Get host from Host table
    public Host getHost() {
        String query = "SELECT * FROM " + TABLE_HOST + ";";
        if (db == null) {
            db = getReadableDatabase();
        }
        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToLast();

        Host host = new Host();
        host.set_hid(Integer.parseInt(c.getString(0)));
        host.setName(c.getString(1));
        host.setLine(c.getString(2));

        try {
            return host;
        } finally {
            c.close();
        }
    }

    // Delete host from Host table
    public void deleteHost() {
        String query = "DELETE FROM " + TABLE_HOST + ";";
        if (db == null)
            db = getWritableDatabase();
        try {
            db.execSQL(query);

            // Add placeholder values for Host table
            addHost("null", "null");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
