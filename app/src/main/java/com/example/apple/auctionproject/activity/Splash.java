package com.example.apple.auctionproject.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.auctionproject.R;

public class Splash extends AppCompatActivity {

    Button login,signup;
    TextView placeBid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView rotateImage = (ImageView) findViewById(R.id.logo);
        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.round_anim);
        startRotateAnimation.setDuration(2000);
        rotateImage.startAnimation(startRotateAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = new TranslateAnimation(0, 0,0, -250);
                animation.setDuration(1000);
                animation.setFillAfter(true);
                rotateImage.startAnimation(animation);
                rotateImage.setVisibility(View.VISIBLE);
            }
        },2000);

        login = (Button) findViewById(R.id.loginIcon);
        signup = (Button) findViewById(R.id.signupIcon);
        placeBid = (TextView) findViewById(R.id.placebidButton);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                login.setVisibility(View.VISIBLE);
                signup.setVisibility(View.VISIBLE);
                placeBid.setVisibility(View.VISIBLE);
            }
        },3000);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Splash.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Splash.this,SignupActivity.class);
                startActivity(i);
                finish();
            }
        });

        placeBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Splash.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
