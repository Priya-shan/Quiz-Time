package com.example.quizworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.*;

public class Quiz_Page extends AppCompatActivity {
    TextView time,crt,wrong;
    TextView qn,a,b,c,d;
    Button next,finish;

    public static final String score="";
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=database.getReference().child("Questions");

    String dbQn;
    String dbAnsA;
    String dbAnsB;
    String dbAnsC;
    String dbAnsD;
    String dbCrtAns;

    int dbQnCnt;
    int qnNum=1;

    int crtanscnt=0;
    int wronganscnt=0;

    String userAnswer;

    CountDownTimer countDownTimer;
    public static final long TOTAL_TIME=25000;
    boolean timer_continue;
    long leftTime=TOTAL_TIME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        time=findViewById(R.id.tvtime);
        crt=findViewById(R.id.tvcrtcnt);
        wrong=findViewById(R.id.tvwrongcnt);
        qn=findViewById(R.id.tvqn);
        a=findViewById(R.id.tva);
        b=findViewById(R.id.tvb);
        c=findViewById(R.id.tvc);
        d=findViewById(R.id.tvd);
        finish=findViewById(R.id.btnfinish);
        next=findViewById(R.id.btnnext);

        game();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                game();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i6=new Intent(Quiz_Page.this,Score_Page.class);
                i6.putExtra(score,crtanscnt+" "+wronganscnt);
                startActivity(i6);

                finish();
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePaused();
                userAnswer="a";
                if(dbCrtAns.equals(userAnswer)){
                    a.setBackgroundColor(Color.GREEN);
                    crtanscnt++;
                    crt.setText(String.valueOf(crtanscnt));
                }
                else{
                    a.setBackgroundColor(Color.RED);
                    wronganscnt++;
                    wrong.setText(String.valueOf(wronganscnt));
                    findAnswer();

                }
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePaused();
                userAnswer="b";
                if(dbCrtAns.equals(userAnswer)){
                    b.setBackgroundColor(Color.GREEN);
                    crtanscnt++;
                    crt.setText(String.valueOf(crtanscnt));
                }
                else{
                    b.setBackgroundColor(Color.RED);
                    wronganscnt++;
                    wrong.setText(String.valueOf(wronganscnt));
                    findAnswer();
                }
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePaused();
                userAnswer="c";
                if(dbCrtAns.equals(userAnswer)){
                    c.setBackgroundColor(Color.GREEN);
                    crtanscnt++;
                    crt.setText(String.valueOf(crtanscnt));

                }
                else{
                    c.setBackgroundColor(Color.RED);
                    wronganscnt++;
                    wrong.setText(String.valueOf(wronganscnt));
                    findAnswer();
                }
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePaused();
                userAnswer="d";
                if(dbCrtAns.equals(userAnswer)){
                    d.setBackgroundColor(Color.GREEN);
                    crtanscnt++;
                    crt.setText(String.valueOf(crtanscnt));
                }
                else{
                    d.setBackgroundColor(Color.RED);
                    wronganscnt++;
                    wrong.setText(String.valueOf(wronganscnt));
                    findAnswer();
                }
            }
        });


    }

    public void game(){
        startTimer();
        a.setBackgroundColor(Color.parseColor("#E3E2E2"));
        b.setBackgroundColor(Color.parseColor("#E3E2E2"));
        c.setBackgroundColor(Color.parseColor("#E3E2E2"));
        d.setBackgroundColor(Color.parseColor("#E3E2E2"));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dbQnCnt=(int)dataSnapshot.getChildrenCount();

                dbQn=dataSnapshot.child(String.valueOf(qnNum)).child("q").getValue().toString();
                dbAnsA=dataSnapshot.child(String.valueOf(qnNum)).child("a").getValue().toString();
                dbAnsB=dataSnapshot.child(String.valueOf(qnNum)).child("b").getValue().toString();
                dbAnsC=dataSnapshot.child(String.valueOf(qnNum)).child("c").getValue().toString();
                dbAnsD=dataSnapshot.child(String.valueOf(qnNum)).child("d").getValue().toString();
                dbCrtAns=dataSnapshot.child(String.valueOf(qnNum)).child("answer").getValue().toString();

                qn.setText(dbQn);
                a.setText(dbAnsA);
                b.setText(dbAnsB);
                c.setText(dbAnsC);
                d.setText(dbAnsD);

                if(qnNum<dbQnCnt){
                    qnNum++;
                }
                else{
                    Toast.makeText(Quiz_Page.this,"ended",Toast.LENGTH_SHORT).show();
                    Intent i6=new Intent(Quiz_Page.this,Score_Page.class);

                    i6.putExtra(score,crtanscnt+" "+wronganscnt);

                    startActivity(i6);
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Quiz_Page.this,"There is an error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void findAnswer(){
        if(dbCrtAns.equals("a")){
            a.setBackgroundColor(Color.GREEN);
        }
        else if(dbCrtAns.equals("b")){
            b.setBackgroundColor(Color.GREEN);
        }
        else if(dbCrtAns.equals("c")){
            c.setBackgroundColor(Color.GREEN);
        }
        else{
            d.setBackgroundColor(Color.GREEN);
        }
    }
    public void startTimer(){
        countDownTimer=new CountDownTimer(leftTime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                leftTime=millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timer_continue=false;
                qn.setText("Sorry Time is Up!");
                timePaused();
            }
        }.start();
        timer_continue=true;
    }
    public void resetTimer(){
        leftTime=TOTAL_TIME;
        updateCountdownText();
    }
    public void updateCountdownText(){
        int second=(int) (leftTime/1000)%60;
        time.setText(""+second);
    }
    public void timePaused(){
        countDownTimer.cancel();
        timer_continue=false;
    }
}