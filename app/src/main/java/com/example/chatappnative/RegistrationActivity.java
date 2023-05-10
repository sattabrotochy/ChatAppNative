package com.example.chatappnative;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity {

    CircleImageView profilerg0;

    EditText rgusername,rgemail,rgpassword,rgrepassword;
    Button signupbutton;
    TextView loginbut;
    FirebaseAuth auth;
    Uri imageURI;
    String imageuri;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;

    String name,password,email,rPassword,status;
    SignUpHandler signUpHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Objects.requireNonNull(getSupportActionBar()).hide();
        bindingView();
        signUpHandler=SignUpHandler.getInstance(getApplicationContext());
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing The Account");
        progressDialog.setCancelable(false);



        loginbut.setOnClickListener(view -> {
            startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            finish();
        });


        signupbutton.setOnClickListener(view -> {
            name=rgusername.getText().toString();
            password=rgpassword.getText().toString();
            email=rgemail.getText().toString();
            rPassword=rgrepassword.getText().toString();
            status="Hey I'm Using This Application";

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(rPassword)){
                progressDialog.dismiss();
                errorToast("Please Enter Valid Information");
            }else  if (!email.matches(emailPattern)){
                progressDialog.dismiss();
               errorToast("Type A Valid Email Here");
            }else if (password.length()<6){
                progressDialog.dismiss();
                errorToast("Password Must Be 6 Characters Or More");
            }else if (!password.equals(rPassword)){
                progressDialog.dismiss();
                errorToast("The Password Doesn't Match");
            }else {

            }

        });

    }

    private void bindingView() {
        profilerg0=findViewById(R.id.profilerg0);
        rgusername=findViewById(R.id.rgusername);
        rgemail=findViewById(R.id.rgemail);
        rgpassword=findViewById(R.id.rgpassword);
        rgrepassword=findViewById(R.id.rgrepassword);
        signupbutton=findViewById(R.id.signupbutton);
        loginbut=findViewById(R.id.loginbut);
    }

    void errorToast(String error){
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    void successToast(String success){
        Toast.makeText(getApplicationContext(), success, Toast.LENGTH_SHORT).show();
    }

}