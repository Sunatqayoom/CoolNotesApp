package com.example.coolnotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText mloginemail,mloginpassword;
    private RelativeLayout mlogin,mgotosignup;
    private TextView mgotoforgotpassword;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressBar mprogressbarofmainacttivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mloginemail=findViewById(R.id.loginemail);
        mloginpassword=findViewById(R.id.loginpassword);
        mlogin=findViewById(R.id.login);
        mgotosignup=findViewById(R.id.gotosignup);
        mgotoforgotpassword=findViewById(R.id.gotoforgotpassword);
        mprogressbarofmainacttivity=findViewById(R.id.progressbarofmainactivity);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null) {
            finish();
            startActivity(new Intent(MainActivity.this,notesActivity.class));
        }
        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Signup.class));
            }
        });
        mgotoforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, forgetpassword.class));

            }
        });
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=mloginemail.getText().toString().trim();
                String password=mloginpassword.getText().toString().trim();
                if(mail.isEmpty()|| password.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"All Fields Are Required",Toast.LENGTH_SHORT).show();
                }
                else {
                    //login the user
                    mprogressbarofmainacttivity.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                checkmailVerification();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Account does not exist",Toast.LENGTH_SHORT).show();
                                mprogressbarofmainacttivity.setVisibility(View.INVISIBLE);
                            }

                        }
                    });

                }
            }
        });

    }
    private void checkmailVerification() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            if (currentUser.isEmailVerified()) {
                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(MainActivity.this, notesActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Verify Your email First", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
            }
        } else {
            // Handle the case where currentUser is null (user not authenticated)

            Toast.makeText(getApplicationContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
            // You might want to redirect the user to the login screen or handle it appropriately
        }
    }


}