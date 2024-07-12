package com.example.smartrecipes;

//Class that manages creation and updating of the recipe database

import android.content.Context;
import android.database.sqlite.SQLiteDatabase; //Represents the database and provides methods for database management
import android.database.sqlite.SQLiteOpenHelper; //Helper class to manage database creation and version management

public class RecipeDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipes.db";
    private static final int DATABASE_VERSION = 1;

    public RecipeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //Create table named recipes with specified columns
        db.execSQL("CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "country TEXT, " +
                "ingredients TEXT, " +
                "instructions TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipes"); //drop existing recipes if table exists already
        onCreate(db);
    }
}
