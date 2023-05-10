package com.example.chatappnative;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginHandler {

    private final   Context mContext;
    FirebaseAuth firebaseAuth;

    private static LoginHandler instance_log=null;
    private  LoginHandler(Context context){
        mContext=context.getApplicationContext();
        firebaseAuth=FirebaseAuth.getInstance();
    }
    public static synchronized LoginHandler getInstance(Context context){
        if(instance_log==null){
            instance_log=new LoginHandler(context);
        }
        return instance_log;
    }




    public void userLogin(String email,String password,LoginListener listener) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    try {
                        if(listener!=null)
                        listener.loginSuccess("Login Successfully done");


                    }catch (Exception e){
                        if (listener!=null)
                            listener.loginFailed(e.getMessage());

                    }
                }else {
                    if(listener!=null)
                    listener.loginFailed(task.getException().getMessage());

                }
            }
        });

    }


public interface LoginListener{
        void loginSuccess(String message);
        void loginFailed(String error);
    }

}
