package com.example.apple.auctionproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.apple.auctionproject.R;
import com.example.apple.auctionproject.model.WalletTransaction;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        List<WalletTransaction> walletTransactions;

        walletTransactions = new ArrayList<>();

        WalletTransaction w = new WalletTransaction("12345","20","Money added","11-dec-2018");
        walletTransactions.add(w);
        WalletTransaction w1 = new WalletTransaction("12345","20","Money added","11-dec-2018");
        walletTransactions.add(w1);

        ListView l = (ListView) findViewById(R.id.transactionList);
        WalletAdapter walletAdapter = new WalletAdapter(this,walletTransactions);

        l.setAdapter(walletAdapter);
    }

    public void back(View view) {
        startActivity(new Intent(WalletActivity.this,MainActivity.class));
    }

    class WalletAdapter extends ArrayAdapter<WalletTransaction> {

        List<WalletTransaction> WalletList;
        Context context;

        public WalletAdapter(Context context, List<WalletTransaction> heroList) {
            super(context, R.layout.transaction_layout, heroList);
            this.context = context;
            this.WalletList = heroList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            //getting the view
            View view = layoutInflater.inflate(R.layout.transaction_layout, null, false);

            //getting the view elements of the list from the view

            return view;
        }
    }
}
