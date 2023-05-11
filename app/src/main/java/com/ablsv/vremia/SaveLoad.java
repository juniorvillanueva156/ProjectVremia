package com.ablsv.vremia;


import android.content.Context;
import android.content.SharedPreferences;

import android.util.Base64;

public class SaveLoad extends AddTask {

    SharedPreferences sharedPreferences, transactionData;
    int transactionCode = 1;

    public void Save(String title, String description, int color,
                     int year, int month, int dayofmonth,
                     int hour, int minute,
                     String imageData, Context context)
    {
        //TODO: Try to code TinyDB into Project
        //Link: https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo
        //Already removed SQL DataBase
    }
}
