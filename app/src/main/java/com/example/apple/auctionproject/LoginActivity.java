package com.example.apple.auctionproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private String mail;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText mailText = (EditText) findViewById(R.id.mailLogin);
        mail = mailText.getText().toString();

        EditText passwordText = (EditText) findViewById(R.id.passwordLogin);
        password = passwordText.getText().toString();

        Button signup = (Button) findViewById(R.id.loginButton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        TextView signuptext = (TextView) findViewById(R.id.signuplogin);

        TextView forgotPassword = (TextView) findViewById(R.id.forgotpassword);

        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                finish();
                startActivity(i);
            }
        });
    }
}
