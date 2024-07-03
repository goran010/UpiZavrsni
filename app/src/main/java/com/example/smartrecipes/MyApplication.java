package com.example.smartrecipes;

import android.app.Application;
import java.util.HashMap;

public class MyApplication extends Application {

    private HashMap<String, String> usersDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        usersDatabase = new HashMap<>();
        // Dodajte unaprijed definirane korisnike ako je potrebno
        usersDatabase.put("user1", "password1");
    }

    public HashMap<String, String> getUsersDatabase() {
        return usersDatabase;
    }
}
