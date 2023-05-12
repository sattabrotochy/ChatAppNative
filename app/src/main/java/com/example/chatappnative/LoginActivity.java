package com.example.chatappnative;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatappnative.Hendler.LoginHandler;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    EditText editTexLogEmail,editTextLogPassword;
    Button logbutton;
    TextView logsignup;

    FirebaseAuth firebaseAuth;

    String email,password;
    LoginHandler loginHandler;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


      bingdingView();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
      firebaseAuth=FirebaseAuth.getInstance();
      loginHandler= LoginHandler.getInstance(getApplicationContext());


        logsignup.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,RegistrationActivity.class)));

        logbutton.setOnClickListener(view -> {
            email=editTexLogEmail.getText().toString().trim();
            password=editTextLogPassword.getText().toString().trim();

            if(email.isEmpty() && password.isEmpty()){
                errorToast("Please Complete all required field");
            }else {
                progressDialog.show();
              loginHandler.userLogin(email, password, new LoginHandler.LoginListener() {
                  @Override
                  public void loginSuccess(String message) {
                      progressDialog.dismiss();
                      successToast(message);
                      startActivity(new Intent(getApplicationContext(),MainActivity.class));
                      finish();
                  }

                  @Override
                  public void loginFailed(String error) {
                      progressDialog.dismiss();
                      errorToast(error);
                  }
              });
            }


        });

    }


    private void bingdingView() {
        logsignup=findViewById(R.id.logsignup);
        logbutton=findViewById(R.id.logbutton);
        editTexLogEmail=findViewById(R.id.editTexLogEmail);
        editTextLogPassword=findViewById(R.id.editTextLogPassword);
    }

    void errorToast(String error){
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    void successToast(String success){
        Toast.makeText(getApplicationContext(), success, Toast.LENGTH_SHORT).show();
    }

}