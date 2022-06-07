package com.example.quizworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Score_Page extends AppCompatActivity {

    ImageView imageView;
    Button playagain,exit;
    TextView crtans_tv,wrongans_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);
        crtans_tv=findViewById(R.id.crtans);
        wrongans_tv=findViewById(R.id.wrongans);
        playagain=findViewById(R.id.playagain);
        exit=findViewById(R.id.exit);

        imageView = findViewById(R.id.imageview);
        Glide.with(this).load(R.drawable.bursttrophydribbbleunscreen).into(imageView);
        Intent i7=getIntent();
        String value=i7.getStringExtra(Quiz_Page.score);
        String[] arr=value.split(" ");
        String crtans=arr[0];
        String wrongans=arr[1];

        crtans_tv.setText(crtans);
        wrongans_tv.setText(wrongans);

        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i8=new Intent(Score_Page.this,MainActivity.class);
                startActivity(i8);
                finish();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    }
}