package com.example.apple.auctionproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apple.auctionproject.R;
import com.example.apple.auctionproject.model.Upcoming_Products;

import java.util.ArrayList;

public class RecyclerViewAdapterUpcoming extends RecyclerView.Adapter<RecyclerViewAdapterUpcoming.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterCurrent";
    String date;
    ArrayList<Upcoming_Products> upcoming_products;


    private Context mContext;

    public RecyclerViewAdapterUpcoming(Context context, ArrayList<Upcoming_Products> upcoming_products) {
        this.upcoming_products = upcoming_products;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_horizontal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        long diff = 0;

        holder.setIsRecyclable(false);

        Glide.with(mContext)
                .asBitmap()
                .load(upcoming_products.get(position).getImage_url())
                .into(holder.image);

        holder.name.setText(upcoming_products.get(position).getTitle());

        holder.mrp.setText(upcoming_products.get(position).getMrp());

        holder.sp.setText(upcoming_products.get(position).getSp());

        holder.btn.setVisibility(View.INVISIBLE);

        //Log.d("this",mImageUrls.get(position)+mNames.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,mNames.get(position),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return upcoming_products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView mrp;
        TextView sp;
        Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.bidbtn);
            sp = itemView.findViewById(R.id.sprp);
            mrp = itemView.findViewById(R.id.mrprp);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}

