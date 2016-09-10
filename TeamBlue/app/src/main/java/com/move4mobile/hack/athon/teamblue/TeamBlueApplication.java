package com.move4mobile.hack.athon.teamblue;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class TeamBlueApplication extends Application {

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
