package com.example.chatappnative;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }
}