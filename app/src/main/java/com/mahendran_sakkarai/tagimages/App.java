package com.mahendran_sakkarai.tagimages;

import android.app.Application;

/**
 * Created by Udhaya Kumar on 26-10-2016.
 */
public class App extends Application {
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static App getInstance() {
        return mInstance;
    }
}
