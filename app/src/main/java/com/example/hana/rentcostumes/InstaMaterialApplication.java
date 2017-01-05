package com.example.hana.rentcostumes;

import android.app.Application;
import timber.log.Timber;
/**
 * Created by user pc on 29/12/2016.
 */

public class InstaMaterialApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
