package com.example.coolnotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetpassword extends AppCompatActivity {
    private EditText mforgotpassword;
    private Button mpasswordRecoverButton;
    private TextView mgotbacktologin;
     FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mforgotpassword=findViewById(R.id.forgetpassword);
        mpasswordRecoverButton=findViewById(R.id.passwordRecoverButton);
        mgotbacktologin=findViewById(R.id.gobacktologin);
        firebaseAuth=FirebaseAuth.getInstance(); //getting instance
        mgotbacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(forgetpassword.this,MainActivity.class);
                startActivity(intent);
            }
        });
        mpasswordRecoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringmail=mforgotpassword.getText().toString().trim();
                if(stringmail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter Your MailID", Toast.LENGTH_SHORT).show();
                }
                else {
                    //send mail
                    firebaseAuth.sendPasswordResetEmail(stringmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Recovery Mail sent to you email id",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgetpassword.this,MainActivity.class));


                            }
                            else {
                                Toast.makeText(getApplicationContext(),"The account does not exist",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}