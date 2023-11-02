package com.example.app_main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        db.execSQL("CREATE TABLE " + TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, user_id TEXT UNIQUE, image BLOB NOT NULL,joining_date TEXT NOT NULL, department TEXT, hourly_rate TEXT)");
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
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Employees_Model> employeeModelArrayList = new ArrayList<>();
        System.out.println("hello");

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            while (cursor.moveToNext()) {
                employeeModelArrayList.add(new Employees_Model(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getBlob(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ));

            }
            System.out.println(employeeModelArrayList);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("readData", "Error fetching data from the database: " + e.getMessage());
        }

        Log.d("readData", "Fetched data: " + employeeModelArrayList);
        return employeeModelArrayList;
    }
    public void updateEmployee(String originalUserid, String id, String userName, byte[] image,
                             String joining_date, String department, String rate) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put("user_id", id);
        values.put("username",userName);
        values.put("image", image);
        values.put("joining_date", joining_date);
        values.put("department", department);
        values.put("hourly_rate", rate);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_NAME, values, "user_id=?", new String[]{originalUserid});
        db.close();
    }
    public void deleteEmployee(String id) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.

        db.delete(TABLE_NAME, "user_id=?", new String[]{id});
        db.close();
    }
}
