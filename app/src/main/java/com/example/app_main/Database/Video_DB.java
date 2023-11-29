package com.example.app_main.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.app_main.Model.Message;
import java.util.ArrayList;
import java.util.List;

public class Video_DB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Video_DB.db";

    public Video_DB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(ID INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT, message TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
    }

    public boolean Insert(String userId, String message) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId", userId);
        contentValues.put("message", message);
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

    public List<Message> readMessagesByUserId(String userId) {
        List<Message> messageList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Assuming you have a "user" table with columns "ID", "userId", and "message"
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM user WHERE userId = ?",
                new String[]{userId}
        );

        if (cursor.moveToFirst()) {
            do {
                // Retrieve data from the cursor
                int messageId = cursor.getInt(cursor.getColumnIndex("ID"));
                String messageText = cursor.getString(cursor.getColumnIndex("message"));
                // Add the message to the list
                messageList.add(new Message(messageId,userId,messageText));
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        sqLiteDatabase.close();

        return messageList;
    }

}
