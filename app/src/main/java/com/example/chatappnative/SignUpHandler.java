package com.example.chatappnative;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpHandler {

    private final Context mContext;

    FirebaseAuth firebaseAuth;

    @SuppressLint("StaticFieldLeak")
    private  static SignUpHandler s_instance=null;

    private SignUpHandler (Context context){
        mContext=context;
    }

    public  static synchronized SignUpHandler getInstance(Context context){
        if(s_instance!=null){
            s_instance=new SignUpHandler(context);
        }
        return s_instance;
    }


    void userRegistration(){

    }

    public  interface signUpListener{
        void onSuccess(String success);
        void onFailed(String error);
    }

}
