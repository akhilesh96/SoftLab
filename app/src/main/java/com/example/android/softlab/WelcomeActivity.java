package com.example.android.softlab;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Akhilesh on 10/31/2017.
 */

public class WelcomeActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
    //just for testing
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefManager = new PrefManager(WelcomeActivity.this);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                if (prefManager.isSignedIn()) {
                    Intent i = new Intent(WelcomeActivity.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
