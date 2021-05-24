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

        //redirection vers la page principale
        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                //demarrer la page
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);
                finish();
            }
        };
        //delai
        new Handler().postDelayed(runnable,1500);
    }
}