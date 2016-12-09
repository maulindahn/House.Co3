package com.example.hana.rentcostumes;

/**
 * Created by Dimas Sartika on 12/9/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class SessionActivity {


    public void setPreferences(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Preference", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void clearPreferences(Context context, String key){
        SharedPreferences.Editor editor = context.getSharedPreferences("Preference", Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.commit();
    }

    public  String getPreferences(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("Preference",	Context.MODE_PRIVATE);
        String position = prefs.getString(key, "");
        return position;
    }
}

