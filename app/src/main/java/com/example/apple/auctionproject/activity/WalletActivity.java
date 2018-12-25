package com.example.apple.auctionproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apple.auctionproject.R;
import com.example.apple.auctionproject.model.WalletTransaction;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

        ImageSpan is = new ImageSpan(this, R.drawable.location);
        String rupee = getResources().getString(R.string.rupee_symbol)+" 300";
        SpannableString text = new SpannableString(rupee);
        text.setSpan(is, 0, 1, 0);

        collapsingToolbar.setTitle(text);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.transactionList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<WalletTransaction> walletTransactions;

        walletTransactions = new ArrayList<>();

        WalletTransaction w = new WalletTransaction("12345","20","Money added","11-dec-2018");
        walletTransactions.add(w);
        WalletTransaction w1 = new WalletTransaction("12345","20","Money added","11-dec-2018");
        walletTransactions.add(w1);
        walletTransactions.add(w1);
        walletTransactions.add(w1);
        walletTransactions.add(w1);
        walletTransactions.add(w1);
        walletTransactions.add(w1);
        walletTransactions.add(w1);
        walletTransactions.add(w1);
        walletTransactions.add(w1);
        walletTransactions.add(w1);walletTransactions.add(w1);
        walletTransactions.add(w1);
        walletTransactions.add(w1);


        //ListView l = (ListView) findViewById(R.id.transactionList);

        WalletAdapter walletAdapter = new WalletAdapter(this, walletTransactions);

        recyclerView.setAdapter(walletAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wallet_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void back(View view) {
        startActivity(new Intent(WalletActivity.this,MainActivity.class));
    }

    public void display(View view) {
        //TextView t = (TextView) findViewById(R.id.viewTransactions);
        ListView l = (ListView) findViewById(R.id.transactionList);

        if(l.getVisibility() == View.VISIBLE){
            l.setVisibility(View.INVISIBLE);
        }
        else
            l.setVisibility(View.VISIBLE);
    }

    class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder>{

        ArrayList<WalletTransaction> walletTransactions;
        Context context;

        WalletAdapter(Context context,ArrayList<WalletTransaction> walletTransactions){
            this.context = context;
            this.walletTransactions = walletTransactions;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_layout, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WalletAdapter.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return walletTransactions.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
