package com.example.smartrecipes;

import android.app.Application;
import java.util.HashMap;


/*
* class used to mantain user db for the app; other parts of the app use it to access and manage user credentials
*/

public class MyApplication extends Application {

    private HashMap<String, String> usersDatabase; //In-memory db to store user credentials

    @Override
    public void onCreate() {
        super.onCreate();
        usersDatabase = new HashMap<>();
        usersDatabase.put("user1", "password1"); //Sample user
    }

    //Provides access to the usersDatabase from other parts of the application by returning the HashMap
    public HashMap<String, String> getUsersDatabase() { //
        return usersDatabase;
    }
}
