package com.mahendran_sakkarai.tagimages;

import android.app.Application;

/**
 * Created by Mahendran Sakkarai on 26-10-2016.
 */
public class BaseApp extends Application {
    private static BaseApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static BaseApp getInstance() {
        return mInstance;
    }
}
