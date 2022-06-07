package com.example.quizworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp_Page extends AppCompatActivity {
    Button signupbutton;
    Button backtologinbutton;
    com.google.android.material.textfield.TextInputEditText email;
    com.google.android.material.textfield.TextInputEditText pw;
    ProgressBar progressBar;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        backtologinbutton=findViewById(R.id.backtologin);
        signupbutton=findViewById(R.id.newsignup);
        email=findViewById(R.id.signupemailid);
        pw=findViewById(R.id.signuppassword);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail=email.getText().toString();
                String userPassword=pw.getText().toString();
                if(userEmail.isEmpty() || userPassword.isEmpty()){
                    Toast.makeText(SignUp_Page.this,
                            "Email and Password Field should not be empty",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    signupFirebase(userEmail, userPassword);
                }
            }
        });
        backtologinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(SignUp_Page.this,Login_Page.class);
                startActivity(i2);
                finish();
            }
        });
    }
    public void signupFirebase(String userEmail,String userPassword){
        signupbutton.setClickable(false);
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUp_Page.this,"Account Created Successfully!!",Toast.LENGTH_SHORT).show();
                            finish();

                        }
                        else{
                            Toast.makeText(SignUp_Page.this,"Oops..!! A Problem occured try again later.",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

}