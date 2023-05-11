package com.example.chatappnative;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatappnative.Hendler.SignUpHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

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

        bindingView();
        signUpHandler = SignUpHandler.getInstance(getApplicationContext());



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

                Log.d(TAG, "onCreate: "+name);
                Log.d(TAG, "onCreate: "+email);
                Log.d(TAG, "onCreate: "+password);
                Log.d(TAG, "onCreate: "+rPassword);
                progressDialog.show();
                signUpHandler.userRegistration(email, password, name,status, new SignUpHandler.SignUpListener() {
                    @Override
                    public void onSuccess(String success) {
                        if(success=="202"){
                            successToast(" Account create successfully done");
                            progressDialog.setCancelable(true);
                            progressDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            successToast("Without image Account create successfully done");
                            progressDialog.setCancelable(true);
                            progressDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailed(String error) {
                        if(error=="500"){
                            progressDialog.setCancelable(true);
                            progressDialog.dismiss();
                            onFailed("Error in creating the user");
                        } else if (error=="501") {
                            progressDialog.setCancelable(true);
                            progressDialog.dismiss();
                            onFailed("Error in creating the user");
                        }else {
                            progressDialog.setCancelable(true);
                            progressDialog.dismiss();
                            onFailed(error);
                        }
                    }
                });
            }

        });
        profilerg0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10){
            if (data!=null){
                imageURI = data.getData();
                Log.d(TAG, "onActivityResult: "+imageURI);
                profilerg0.setImageURI(imageURI);
            }
        }
    }

}