package com.example.craig.firebaseread.Pages;

import com.google.firebase.database.FirebaseDatabase;

/*
This class is used to set up the persistent state of the database.
It saves the data of the database to a local copy on the device
allowing for use offline. It must be made static to keep track of between pages
 */
public class DatabasePersistentSetUp {
    private static FirebaseDatabase database;

    public static FirebaseDatabase getDatabase(){
        if(database == null){
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        return database;
    }
}
