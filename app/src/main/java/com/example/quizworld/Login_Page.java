package com.example.quizworld;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login_Page extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText emailid;
    com.google.android.material.textfield.TextInputEditText pw;
    Button forgotpw;
    Button signin;
    SignInButton googlesignin;
    Button callsignup;
    ProgressBar loginprogbar;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    GoogleSignInClient googleSignInClient;
    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        registerActivityForGoogleSignIn();
        emailid=findViewById(R.id.emailid);
        pw=findViewById(R.id.password);
        forgotpw=findViewById(R.id.forgotpw);
        signin=findViewById(R.id.signin);
        googlesignin=findViewById(R.id.signinwithgoogle);
        callsignup=findViewById(R.id.signup);
        loginprogbar=findViewById(R.id.loginprogressbar);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=emailid.getText().toString();
                String userpassword=pw.getText().toString();
                if(useremail.isEmpty() || userpassword.isEmpty()){
                    Toast.makeText(Login_Page.this,
                            "Email and Password Field should not be empty",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    signInWithFirebase(useremail, userpassword);
                }
            }
        });
        googlesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinGoogle();
            }
        });
        forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i5=new Intent(Login_Page.this,Forgot_Password.class);
                startActivity(i5);
            }
        });
        callsignup.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                Intent i1=new Intent(Login_Page.this,SignUp_Page.class);
                startActivity(i1);
            }
        });
    }
    public void signInWithFirebase(String useremail,String userpassword){
        //signin.setClickable(false);
        loginprogbar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(useremail,userpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            loginprogbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Login_Page.this,
                                    "SignIn Successfull",
                                    Toast.LENGTH_SHORT).show();
                            Intent i3=new Intent(Login_Page.this,MainActivity.class);
                            startActivity(i3);
                            finish();

                        }
                        else{
                            Toast.makeText(Login_Page.this,"Oops..!! A Problem occured try again later.",Toast.LENGTH_SHORT).show();
                            loginprogbar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null){
            Intent i3=new Intent(Login_Page.this,MainActivity.class);
            startActivity(i3);
            finish();
        }
    }
    public void signinGoogle(){
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("42636515367-slh43671h7nrgi2m3al9ie3fhhniu9g4.apps.googleusercontent.com")
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this,gso);
        signin();
    }

    private void signin() {
        Intent SignInIntent=googleSignInClient.getSignInIntent();
        activityResultLauncher.launch(SignInIntent);
    }
    public void registerActivityForGoogleSignIn(){
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
            @Override
            public void onActivityResult(ActivityResult result){
                int resultCode=result.getResultCode();
                Intent data=result.getData();

                if(resultCode== RESULT_OK && data!=null){
                    Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
                    firebaseSignInWithGoogle(task);
                }
            }
        });
    }
    private void firebaseSignInWithGoogle(Task<GoogleSignInAccount> task){
        try{
            GoogleSignInAccount account=task.getResult(ApiException.class);
            Toast.makeText(this,"Successfully",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(Login_Page.this,MainActivity.class);
            startActivity(i);
            finish();
            firebaseGoogleAccount(account);
        }
        catch(ApiException e){
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    private void firebaseGoogleAccount(GoogleSignInAccount account){
        AuthCredential authCredential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //FirebaseUser user= auth.getCurrentUser();

                        }
                        else{

                        }
                    }
                });
    }
}