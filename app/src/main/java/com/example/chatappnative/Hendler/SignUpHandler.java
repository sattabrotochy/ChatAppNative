package com.example.chatappnative.Hendler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.chatappnative.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SignUpHandler {

    private final Context mContext;
    Uri imageURI;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    @SuppressLint("StaticFieldLeak")
    private  static SignUpHandler s_instance=null;

    private SignUpHandler (Context context){
        mContext=context.getApplicationContext();
        firebaseAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

    }

    public static synchronized SignUpHandler getInstance(Context context) {
        if (s_instance == null) {
            s_instance = new SignUpHandler(context);
        }
        return s_instance;
    }



    public  void userRegistration(String email,String password,String name,String status,SignUpListener signUpListener){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = task.getResult().getUser().getUid();
                    DatabaseReference reference = database.getReference().child("user").child(id);
                    StorageReference storageReference = storage.getReference().child("Upload").child(id);
                    if (imageURI!=null){
                        storageReference.putFile(imageURI).addOnCompleteListener(task1 -> {
                            if(task.isSuccessful()){
                              storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                  UserModel users = new UserModel(id,name,email,password,uri.toString(),status);
                                  reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                      @SuppressLint("SuspiciousIndentation")
                                      @Override
                                      public void onComplete(@NonNull Task<Void> task) {
                                          if (task.isSuccessful()){
                                              if(signUpListener!=null)
                                             signUpListener.onSuccess("202");
                                          }else {
                                              if(signUpListener!=null)
                                              signUpListener.onFailed("500");
                                          }
                                      }
                                  });

                              });
                            }
                        });

                    }else {
                        String status = "Hey I'm Using This Application";
                       String imageuri = "https://firebasestorage.googleapis.com/v0/b/av-messenger-dc8f3.appspot.com/o/man.png?alt=media&token=880f431d-9344-45e7-afe4-c2cafe8a5257";
                        UserModel users = new UserModel(id,name,email,password,imageuri,status);
                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    if(signUpListener!=null)
                                    signUpListener.onSuccess("201");
                                }else {
                                    if(signUpListener!=null)
                                   signUpListener.onFailed("501");
                                }
                            }
                        });
                    }

                }else {
                    if(signUpListener!=null)
                    signUpListener.onFailed(task.getException().getMessage());
                }

            }
        });
    }




    public  interface SignUpListener{
        void onSuccess(String success);
        void onFailed(String error);
    }

}
