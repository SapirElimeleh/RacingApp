package com.example.racingapp;

import android.content.Context;
import android.content.SharedPreferences;

public class MSP {

    private static final String SP_FILE = "SharedPrefs";
    private static MSP msp;
    private SharedPreferences sharedPreferences;


    public static MSP getInstance() {
        return msp;
    }

    private MSP(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
    }

    public static MSP initMsp(Context context) {
        if (msp == null) {
            msp = new MSP(context);
        }
        return msp;
    }

    public void putDouble(String KEY, double defValue) {
        putString(KEY, String.valueOf(defValue));
    }

    public double getDouble(String KEY, double defValue) {
        return Double.parseDouble(getString(KEY, String.valueOf(defValue)));
    }

    public int getInt(String KEY, int defValue) {
        return sharedPreferences.getInt(KEY, defValue);
    }

    public void putInt(String KEY, int value) {
        sharedPreferences.edit().putInt(KEY, value).apply();
    }

    public String getString(String KEY, String defValue) {
        if(sharedPreferences==null){
            return null;
        }else
            return sharedPreferences.getString(KEY, defValue);
    }

    public void putString(String Key, String value) {
        sharedPreferences.edit().putString(Key, value).apply();
    }


}
