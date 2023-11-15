package com.example.app_main;

import static android.app.ProgressDialog.show;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {private static final String DATABASE_NAME = "WorkTimeDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_WORKTIME = "WorkTime";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_ID = "user_name";
    public static final String COLUMN_CLOCK_IN = "clock_in_time";
    public static final String COLUMN_CLOCK_OUT = "clock_out_time";

    public static final String COLUMN_BREAK_IN = "break_in_time";
    public static final String COLUMN_BREAK_OUT = "break_out_time";
    public static final String COLUMN_TOTAL_HOURS = "total_hours";
private SQLiteDatabase database;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String DATABASE_CREATE = "create table "
                + TABLE_WORKTIME + "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_USER_ID + " text, "
                + COLUMN_CLOCK_IN + " text, "
                + COLUMN_CLOCK_OUT + " text, "
                + COLUMN_BREAK_IN + " text, "
                + COLUMN_BREAK_OUT + " text, "
                + COLUMN_TOTAL_HOURS + " text);";
        database.execSQL(DATABASE_CREATE);
    }
    //data insertion in database
    public long insertWorkTime(Date clockIn, Date clockOut, Date breakIn, Date breakOut, String totalHours, String user_name) {
        long rowId = -1;

        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            values.put(COLUMN_USER_ID, (user_name != null) ? user_name : null);
            values.put(COLUMN_CLOCK_IN, (clockIn != null) ? sdf.format(clockIn) : null);
            values.put(COLUMN_CLOCK_OUT, (clockOut != null) ? sdf.format(clockOut) : null);
            values.put(COLUMN_BREAK_IN, (breakIn != null) ? sdf.format(breakIn) : null);
            values.put(COLUMN_BREAK_OUT, (breakOut != null) ? sdf.format(breakOut) : null);
            values.put(COLUMN_TOTAL_HOURS, totalHours);

            rowId = db.insert(TABLE_WORKTIME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowId;
    }

    public void updateBreakInTime(long rowId, Date breakInTime) {
        if (!database.isOpen()) {
            database = getWritableDatabase(); // Reopen the database if closed
        }
        ContentValues values = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(COLUMN_BREAK_IN, (breakInTime != null) ? sdf.format(breakInTime) : null);

        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(rowId)};

        database.update(TABLE_WORKTIME, values, whereClause, whereArgs);
    }

    // Update break out time
    public void updateBreakOutTime(long rowId, Date breakOutTime) {
        if (!database.isOpen()) {
            database = getWritableDatabase(); // Reopen the database if closed
        }
        ContentValues values = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(COLUMN_BREAK_OUT, (breakOutTime != null) ? sdf.format(breakOutTime) : null);

        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(rowId)};

        database.update(TABLE_WORKTIME, values, whereClause, whereArgs);
    }

    // Update clock out time
    public void updateClockOutTime(long rowId, Date clockOutTime, String totalHours) {
        if (!database.isOpen()) {
            database = getWritableDatabase(); // Reopen the database if closed
        }
        ContentValues values = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(COLUMN_TOTAL_HOURS, totalHours);
        values.put(COLUMN_CLOCK_OUT, (clockOutTime != null) ? sdf.format(clockOutTime) : null);

        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(rowId)};

        database.update(TABLE_WORKTIME, values, whereClause, whereArgs);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS "+ TABLE_WORKTIME);
onCreate(db);
        // Handle database schema upgrades here
    }
}
