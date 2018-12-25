package com.example.apple.auctionproject.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.apple.auctionproject.R;
import com.example.apple.auctionproject.model.CartItems;

import java.util.ArrayList;
import java.util.List;

public class MyCart extends AppCompatActivity {

    public List<CartItems> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        ListView cartList = (ListView) findViewById(R.id.myBidsList);

        cartItems = new ArrayList<>();

        CartItems c = new CartItems("http://easyvela.esy.es/CurrentProductImages/Apple.jpg","300","Active","Apple");
        cartItems.add(c);
        cartItems.add(c);

        CartAdapter cartAdapter = new CartAdapter(this,cartItems);
        cartList.setAdapter(cartAdapter);
    }

    class CartAdapter extends ArrayAdapter<CartItems> {

        List<CartItems> CartList;
        Context context;

        public CartAdapter(Context context, List<CartItems> heroList) {
            super(context, R.layout.transaction_layout, heroList);

            this.context = context;
            this.CartList = heroList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            //getting the view
            View view = layoutInflater.inflate(R.layout.mybid_layout, null, false);

            //getting the view elements of the list from the view

            ImageView image = (ImageView) view.findViewById(R.id.myBidImage);

            Glide.with(context)
                    .asBitmap()
                    .load("http://easyvela.esy.es/CurrentProductImages/Apple.jpg")
                    .into(image);

            return view;
        }
    }
}
