package com.example.apple.auctionproject.other;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.apple.auctionproject.activity.LoginActivity;
import com.example.apple.auctionproject.model.User;

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "easyvelasharedpref";
    private static final String KEY_MOBILE = "keymobile";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_FIRSTNAME = "keyname";
    private static final String KEY_LASTNAME = "keylastname";
    private static final String KEY_ADDRESS = "keyaddress";
    private static final String KEY_PINCODE = "keypincode";
    private static final String KEY_ID = "keyid";
    private static final String KEY_BALANCE = "keybalance";
    private static final String KEY_USERNAME = "keyusername";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, user.getId());
        editor.putString(KEY_MOBILE, user.getContactno());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_FIRSTNAME, user.getFirstName());
        editor.putString(KEY_ADDRESS,user.getAddress());
        editor.putString(KEY_BALANCE,user.getBalance());
        editor.putString(KEY_PINCODE,user.getPincode());
        editor.putString(KEY_USERNAME,user.getUsername());
        editor.putString(KEY_LASTNAME,user.getLastname());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MOBILE, null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_FIRSTNAME, null),
                sharedPreferences.getString(KEY_LASTNAME, null),
                sharedPreferences.getString(KEY_BALANCE, null),
                sharedPreferences.getString(KEY_ADDRESS,null),
                sharedPreferences.getString(KEY_MOBILE,null),
                sharedPreferences.getString(KEY_PINCODE,null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(mCtx,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }
}