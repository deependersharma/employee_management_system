package com.example.app_main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LoginDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "login.db";
    public static final String TABLE_NAME = "user";

    public LoginDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, user_id TEXT, image BLOB NOT NULL,joining_date TEXT NOT NULL, department TEXT, hourly_rate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
    }

    public boolean Insert(String id, String username, byte[] img, String date, String department, String rate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("user_id", id);
        contentValues.put("image",img);
        contentValues.put("joining_date",date);
        contentValues.put("department", department);
        contentValues.put("hourly_rate",rate);
        long result = sqLiteDatabase.insert("user", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean CheckUsername(String username) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE username=?", new String[]{username});
        if (cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean CheckLogin(String username, String user_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE username=? AND user_id=?", new String[]{username, user_id});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<Employees_Model> readData() {
        // Step 1: on below line we are opening the
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        //  Step2: A Cursor in Android is an interface for accessing and retrieving data
        //  from a database query result. In this case, it will be used to store the result of the SQL query.
        //Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NAME_COL + "=?", new String[]{courseName});
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        //The second parameter is an array of strings that can be
        // used to replace placeholders in the SQL query with actual values

        // on below line we are creating a new array list.
        ArrayList<Employees_Model> Employee_ModalArrayList = new ArrayList<>();

        while(cursorCourses.moveToNext()) {
            // on below line we are adding the data from cursor to our array list.
            Employee_ModalArrayList.add(new Employees_Model(cursorCourses.getString(1),
                    cursorCourses.getString(2),
                    cursorCourses.getBlob(3),
                    cursorCourses.getString(4),
                    cursorCourses.getString(5),
                    cursorCourses.getString(6)));
        }
        System.out.println(Employee_ModalArrayList);
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();

        return Employee_ModalArrayList;
    }
}
