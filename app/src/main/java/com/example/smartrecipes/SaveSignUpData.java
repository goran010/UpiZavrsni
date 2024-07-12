package com.example.smartrecipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SaveSignUpData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    //Declaration of constants for the table and column names

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public SaveSignUpData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL statement to create the users table with the specified columns
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE); //Executes the SQL statement to create the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //called when db needs to be upgraded
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS); //Drop the existing users table if it exists
        onCreate(db);
    }

    public boolean addUser(String username, String password) { //Adds new user to the db
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //Create a new set of values
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        //Inserts the values into the users table and returns the row ID of the newly inserted row,
        //or -1 if an error occurred
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }


    //Check if a user with the given username and password exists in the database
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_ID }; //Return column -> ID
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?"; //Selection criteria
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount(); //Get number of rows in the result set
        cursor.close();
        db.close();
        return count > 0;
    }

    //Check if a user with the given username exists in the database
    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_ID };
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }
}