package com.deepesh.imagevideogallery.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deepesh.imagevideogallery.R;
import com.deepesh.imagevideogallery.model.User;
import com.deepesh.imagevideogallery.model.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Login extends AppCompatActivity implements View.OnClickListener {


    EditText user_email;
    EditText user_pass;
    Button btn_login;

    String email,pass,token;
    User user;

    FirebaseUser fuser;
    DatabaseReference dref;
    FirebaseAuth fauth;
    StorageReference sref;
    FirebaseAuth.AuthStateListener flisAuthStateListener;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = fauth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user_email=findViewById(R.id.user_email);
        user_pass=findViewById(R.id.user_pass);
        btn_login=findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "Login Clicked", Toast.LENGTH_LONG).show();
                loginUser();

            }
        });

        FirebaseApp.initializeApp(this);
        //sref=FirebaseStorage.getInstance().getReference();
        dref=FirebaseDatabase.getInstance().getReference();
        fauth=FirebaseAuth.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void loginUser(){
        if (!validateForm()){
            Toast.makeText(Login.this, "validation faield", Toast.LENGTH_SHORT).show();
            return;
        }




        try {
            fauth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
//                        email=user_email.getText().toString().trim();
//                        pass=user_pass.getText().toString().trim();
//                        String name="Deepesh Kumar";
//                        String mob="9803475225";
//                        String email="deepeshkumar2580@gmail.com";
//                        String imageurl="aavvsvvsvvsvs";
//
//                       fuser=fauth.getCurrentUser();
//                       System.out.println("User == "+fuser.toString());
//
//                        token=FirebaseInstanceId.getInstance().getToken();
//                        user=new User(name,email,mob,pass,imageurl,token);
//
//                        Toast.makeText(Login.this, user.toString(), Toast.LENGTH_SHORT).show();
//                        Log.d("shs",user.toString());
//                        dref.child("AdminUser").child(fuser.getUid()).setValue(user);
                        Intent intent=new Intent(Login.this, HomeActivity.class);
                        intent.putExtra("intent_user",user.toString());
                        startActivity(intent);


                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Login.this, "Login UnSuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }catch (Exception e){
            Toast.makeText(Login.this, "Login UnSuccessful Due to Exception", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        /*try {
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user=dataSnapshot.getValue(User.class);

                    Toast.makeText(Login.this, user.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(Login.this, "555555", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }*/

    }
    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(user_email.getText().toString())) {
            user_email.setError("Required");
            result = false;
        } else {
            user_email.setError(null);
        }

        if (TextUtils.isEmpty(user_pass.getText().toString())) {
            user_pass.setError("Required");
            result = false;
        } else {
            user_pass.setError(null);
        }

        return result;
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){

        }

//        if (id==R.id.btn_login){
//            Toast.makeText(Login.this, "Login Clicked", Toast.LENGTH_LONG).show();
//            loginUser();
//
//        }else {
//
//        }
    }
}
