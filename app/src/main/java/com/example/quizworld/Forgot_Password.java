package com.example.quizworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText resetemail;
    Button resetbtn;
    ProgressBar progressBar;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        resetemail=findViewById(R.id.resetemail);
        resetbtn=findViewById(R.id.resetbtn);
        progressBar=findViewById(R.id.progressBarinPwReset);

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=resetemail.getText().toString();
                resetpwwithfirebase(useremail);
            }
        });
    }
    public void resetpwwithfirebase(String useremail){
        progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(useremail)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Forgot_Password.this,
                                    "Password reset Email was sent",
                                    Toast.LENGTH_SHORT).show();
                            finish();

                        }
                        else{
                            Toast.makeText(Forgot_Password.this,"Oops..!! A Problem occured try again later.",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}