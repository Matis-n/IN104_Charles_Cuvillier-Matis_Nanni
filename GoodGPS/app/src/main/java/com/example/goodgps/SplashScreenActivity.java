package com.example.goodgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //rediriger vers l'appli apres un delai
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //demarrer une page
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);
                finish();
            }
        };
        // handler post delayed
        new Handler().postDelayed(runnable, 1500);

    }
}