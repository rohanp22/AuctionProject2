package com.example.apple.auctionproject.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.auctionproject.R;
import com.example.apple.auctionproject.other.RequestHandler;
import com.example.apple.auctionproject.other.SharedPrefManager;
import com.example.apple.auctionproject.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText etmobile,etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etmobile = (EditText) findViewById(R.id.mailLogin);
        etpassword = (EditText) findViewById(R.id.passwordLogin);
        Button login = (Button) findViewById(R.id.loginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
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

    private void userLogin() {

        final String mobile = etmobile.getText().toString();
        final String password = etpassword.getText().toString();

        if (TextUtils.isEmpty(mobile)) {
            etmobile.setError("Please enter Mobile Number");
            etmobile.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etpassword.setError("Please enter Password");
            etpassword.requestFocus();
            return;
        }

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getString("id"),
                                userJson.getString("username"),
                                userJson.getString("email"),
                                userJson.getString("firstname"),
                                userJson.getString("lastname"),
                                userJson.getString("balance"),
                                userJson.getString("address"),
                                userJson.getString("contactno"),
                                userJson.getString("pincode")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid mobile number or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("mobile", mobile);
                params.put("password", password);

                //returing the response
                return requestHandler.sendPostRequest("LoginURL", params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
