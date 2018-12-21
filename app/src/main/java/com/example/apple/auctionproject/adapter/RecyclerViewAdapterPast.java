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
import com.example.apple.auctionproject.model.Past_Products;

import java.util.ArrayList;

public class RecyclerViewAdapterPast extends RecyclerView.Adapter<RecyclerViewAdapterPast.ViewHolder> {

    ArrayList<Past_Products> past_prodcuts;


    private Context mContext;

    public RecyclerViewAdapterPast(Context context, ArrayList<Past_Products> past_products) {
        this.past_prodcuts = past_products;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_horizontal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        holder.btn.setVisibility(View.GONE);

        Glide.with(mContext)
                .asBitmap()
                .load(past_prodcuts.get(position).getImage_url())
                .into(holder.image);

        holder.name.setText(past_prodcuts.get(position).getTitle());

        holder.mrp.setText(past_prodcuts.get(position).getMrp());

        holder.sp.setText(past_prodcuts.get(position).getSp());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,mNames.get(position),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return past_prodcuts.size();
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
