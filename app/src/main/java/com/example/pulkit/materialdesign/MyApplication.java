package com.example.pulkit.materialdesign;

import android.app.Application;
import android.content.Context;

/**
 * Created by pulkit on 8/26/15.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;
    public static final String API_KEY_ROTTEN_TOMATOS = "54wzfswsa4qmjg8hjwa64d4c";
    public static final String API_KEY_THE_MOVIE_DB = "8e88fa3a9288181e4c16a2a3c9c5d47f";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getsInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
