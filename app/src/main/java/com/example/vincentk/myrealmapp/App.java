package com.example.vincentk.myrealmapp;

import android.app.Application;
import android.content.res.Configuration;

import io.realm.Realm;

/**
 * Created by vincentk on 26/06/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}