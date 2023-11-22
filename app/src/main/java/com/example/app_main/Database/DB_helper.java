package com.example.app_main.Database;

import static android.app.ProgressDialog.show;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DB_helper extends SQLiteOpenHelper {private static final String DATABASE_NAME = "WorkTimeDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_WORKTIME = "WorkTime";
    public static final String COLUMN_ID = "id";

    public static final String COLUMN_USER_ID= "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_CLOCK_IN = "clock_in_time";
    public static final String COLUMN_CLOCK_OUT = "clock_out_time";

    public static final String COLUMN_BREAK_IN = "break_in_time";
    public static final String COLUMN_BREAK_OUT = "break_out_time";
    public static final String COLUMN_TOTAL_HOURS = "total_hours";
private SQLiteDatabase database;


    public DB_helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String DATABASE_CREATE = "create table "
                + TABLE_WORKTIME + "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_USER_ID + " text, "
                + COLUMN_USER_NAME + " text, "
                + COLUMN_CLOCK_IN + " text, "
                + COLUMN_CLOCK_OUT + " text, "
                + COLUMN_BREAK_IN + " text, "
                + COLUMN_BREAK_OUT + " text, "
                + COLUMN_TOTAL_HOURS + " text);";
        database.execSQL(DATABASE_CREATE);
    }
    //data insertion in database
    public long insertWorkTime(Date clockIn, String user_name, String user_id) {
        long rowId = -1;

        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            values.put(COLUMN_USER_ID, (user_id != null) ? user_id : null);
            values.put(COLUMN_USER_NAME, (user_name != null) ? user_name : null);
            values.put(COLUMN_CLOCK_IN, (clockIn != null) ? sdf.format(clockIn) : null);
//            values.put(COLUMN_CLOCK_OUT, (clockOut != null) ? sdf.format(clockOut) : null);
//            values.put(COLUMN_BREAK_IN, (breakIn != null) ? sdf.format(breakIn) : null);
//            values.put(COLUMN_BREAK_OUT, (breakOut != null) ? sdf.format(breakOut) : null);
//            values.put(COLUMN_TOTAL_HOURS, totalHours);

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

    public ArrayList<Employee> getEmployeesWithClockInValues() {
        ArrayList<Employee> employeesWithClockInValues = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT user_id, user_name FROM WorkTime WHERE clock_in_time IS NOT NULL AND clock_in_time != '' AND clock_out_time IS NULL", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("user_id"));
                String name = cursor.getString(cursor.getColumnIndex("user_name"));

                // Create an Employee object with the retrieved data
                Employee employee = new Employee(id, name);
                employeesWithClockInValues.add(employee);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return employeesWithClockInValues;
    }
    public ArrayList<Employee> getEmployeesOnBreak() {
        ArrayList<Employee> employeesOnBreak = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT user_id, user_name FROM WorkTime WHERE break_in_time IS NOT NULL AND break_out_time IS NULL", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("user_id"));
                String name = cursor.getString(cursor.getColumnIndex("user_name"));

                // Create an Employee object with the retrieved data
                Employee employee = new Employee(id, name);
                employeesOnBreak.add(employee);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return employeesOnBreak;
    }
    public ArrayList<Employee> getEmployeesFinishedShift() {
        ArrayList<Employee> employeesFinishedShift = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT user_id, user_name FROM WorkTime WHERE clock_out_time IS NOT NULL", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("user_id"));
                String name = cursor.getString(cursor.getColumnIndex("user_name"));

                // Create an Employee object with the retrieved data
                Employee employee = new Employee(id, name);
                employeesFinishedShift.add(employee);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return employeesFinishedShift;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS "+ TABLE_WORKTIME);
onCreate(db);
        // Handle database schema upgrades here
    }

}
