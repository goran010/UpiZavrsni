package com.example.smartrecipes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    //Declaration
    private static final String DATABASE_NAME = "userDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public UserDatabaseHelper(Context context) {
        //Call constructor of the SQLiteOpenHelper class
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create SQL statement to create the users table with the specified columns
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE); //Execution of the statement
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS); //Drop table if exists already
        onCreate(db);
    }

    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username); //Add username to values
        values.put(COLUMN_PASSWORD, password); //Add password to values

        long result = db.insert(TABLE_USERS, null, values);
        db.close();

        return result != -1; //Return true if insert is successful
    }

    public boolean checkUser(String username, String password) {
        //Check if user already exists
        SQLiteDatabase db = this.getReadableDatabase();
        //Queries the users table and returns a cursor over the result set
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0; //True if exists
    }
}
