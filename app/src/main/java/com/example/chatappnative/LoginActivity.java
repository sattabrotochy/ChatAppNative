package com.example.chatappnative;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    EditText editTexLogEmail,editTextLogPassword;
    Button logbutton;
    TextView logsignup;

    FirebaseAuth firebaseAuth;

    String email,password;
    LoginHandler loginHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


      bingdingView();
      firebaseAuth=FirebaseAuth.getInstance();
      loginHandler= LoginHandler.getInstance(getApplicationContext());


        logsignup.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,RegistrationActivity.class)));

        logbutton.setOnClickListener(view -> {
            email=editTexLogEmail.getText().toString().trim();
            password=editTextLogPassword.getText().toString().trim();

            if(email.isEmpty() && password.isEmpty()){
                errorToast("Please Complete all required field");
            }else {
              loginHandler.userLogin(email, password, new LoginHandler.LoginListener() {
                  @Override
                  public void loginSuccess(String message) {
                      successToast(message);
                      startActivity(new Intent(getApplicationContext(),MainActivity.class));
                      finish();
                  }

                  @Override
                  public void loginFailed(String error) {
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