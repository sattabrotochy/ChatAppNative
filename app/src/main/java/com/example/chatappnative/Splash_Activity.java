package com.example.chatappnative;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class Splash_Activity extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);


        new Handler().postDelayed(() -> {

            startActivity(new Intent(Splash_Activity.this, MainActivity.class));

        }, 1500);
    }
}