package com.example.android.softlab;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Akhilesh on 10/31/2017.
 */

public class PrefManager {

    // Shared preferences file name
    private static final String PREF_NAME = "softlab";
    private static final String IS_SIGNED_IN = "IsSignedIn";

    private static final String URL = "URL";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    private static final String endpoint="";
    // shared pref mode
    int PRIVATE_MODE = 0;

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.putString(URL,endpoint );
        editor.commit();
    }

    public String getUrl(){
        return pref.getString(URL,"");
    }

    public boolean isSignedIn() {
        return pref.getBoolean(IS_SIGNED_IN, false);
    }

    public void setIsSignedIn(boolean signedIn) {
        editor.putBoolean(IS_SIGNED_IN, signedIn);
        editor.commit();
    }

}
