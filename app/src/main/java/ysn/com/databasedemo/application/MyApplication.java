package ysn.com.databasedemo.application;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {

    private static Context applicationContext;

    public static Context getInstance() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }
}
