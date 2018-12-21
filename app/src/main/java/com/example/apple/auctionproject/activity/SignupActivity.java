package com.example.apple.auctionproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apple.auctionproject.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText mail = (EditText) findViewById(R.id.emailSignup);
        EditText name = (EditText) findViewById(R.id.nameSignup);
        EditText phone = (EditText) findViewById(R.id.phoneSignup);
        EditText password = (EditText) findViewById(R.id.passwordSignup);

        String mailText = mail.getText().toString();
        String nameText = name.getText().toString();
        String phoneText = phone.getText().toString();
        String passwordText = password.getText().toString();

        Button signupbutton = (Button) findViewById(R.id.signupbutton);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this,MainActivity.class);
                finish();
                startActivity(i);
            }
        });
    }
}
