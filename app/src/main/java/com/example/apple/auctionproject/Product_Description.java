package com.example.apple.auctionproject;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.relex.circleindicator.CircleIndicator;

public class Product_Description extends AppCompatActivity {

    ViewPager viewPager;
    ImageView imageView;
    int position1;
    TextView title;
    public ArrayList<Current_Product> current_products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__description);
        Intent i = getIntent();
        int pos = Integer.parseInt(i.getStringExtra("id"));
        position1 = pos;

        title = (TextView) findViewById(R.id.titleProduct);
        imageView = (ImageView) findViewById(R.id.productImage);
        loadCurrentProducts();


    }

    void loadCurrentProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://easyvela.esy.es/AndroidAPI/currentproducts.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            current_products.clear();
                            JSONObject obj = new JSONObject(response);
                            JSONArray heroArray = obj.getJSONArray("Current_Products");
                            for (int i = 0; i < heroArray.length(); i++) {
                                JSONObject heroObject = heroArray.getJSONObject(i);
                                Current_Product c = new Current_Product(heroObject.getString("currentid"),
                                        heroObject.getString("image_url"),
                                        heroObject.getString("title"),
                                        heroObject.getString("endtime"),
                                        heroObject.getString("mrp"),
                                        heroObject.getString("sp"));
                                current_products.add(c);
                            }
                            initRecycler();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    class RecyclerViewAdapterCurrent extends RecyclerView.Adapter<RecyclerViewAdapterCurrent.ViewHolder>{

        private static final String TAG = "RecyclerViewAdapterCurrent";
        long diff;
        Date startDate1;
        String date;
        ArrayList<Current_Product> current_products;


        private Context mContext;

        public RecyclerViewAdapterCurrent(Context context, ArrayList<Current_Product> current_product) {
            this.current_products = current_product;
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_horizontal, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapterCurrent.ViewHolder holder, final int position) {

            long diff = 0;

            holder.setIsRecyclable(false);

            date = current_products.get(position).getEnd_date();

            Log.d("dates : ",date+"");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

            try {
                startDate1 = simpleDateFormat.parse(current_products.get(position).getEnd_date());
                diff = startDate1.getTime() - System.currentTimeMillis();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(mContext)
                    .asBitmap()
                    .load(current_products.get(position).getImage_url())
                    .into(holder.image);

            holder.name.setText(current_products.get(position).getTitle());

            holder.mrp.setText(current_products.get(position).getMrp());

            holder.sp.setText(current_products.get(position).getSp());

            //Log.d("this",mImageUrls.get(position)+mNames.get(position));

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Product_Description.this,Product_Description.class);
                    i.putExtra("id",position+"");
                    Log.d("Position",position+"");
                    startActivity(i);
                    finish();
                }
            });

            final long secondsInMilli = 1000;
            final long minutesInMilli = secondsInMilli * 60;
            final long hoursInMilli = minutesInMilli * 60;
            final long daysInMilli = hoursInMilli * 24;

            final long finalDiff = diff;

            new CountDownTimer(finalDiff, 1000) {

                long dif = finalDiff;

                public void onTick(long millisUntilFinished) {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

                    try {
                        startDate1 = simpleDateFormat.parse(current_products.get(position).getEnd_date());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long different = Math.abs(startDate1.getTime() - System.currentTimeMillis());

                    final long elapsedDays = different / daysInMilli;

                    final long elapsedHours = different / hoursInMilli;
                    different = different % hoursInMilli;

                    final long elapsedMinutes = different / minutesInMilli;
                    different = different % minutesInMilli;

                    final long elapsedSeconds = different / secondsInMilli;
                    Log.d("timerem", elapsedDays + ":" + elapsedHours + ":" + elapsedMinutes + ":" + elapsedSeconds);
                    String curtime = elapsedHours + ":" + elapsedMinutes + ":" + elapsedSeconds;
                    holder.time.setText(curtime);
                }

                public void onFinish() {
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://easyvela.esy.es/AndroidAPI/currentmove.php?id=" + current_products.get(position).getId(),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(mContext, response, Toast.LENGTH_LONG);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                    requestQueue.add(stringRequest);
                    finish();
                    startActivity(getIntent());
                }
            }.start();

            holder.bid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return current_products.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            Button bid;
            ImageView image;
            TextView name;
            TextView time;
            TextView mrp;
            TextView sp;

            public ViewHolder(View itemView) {
                super(itemView);
                bid = itemView.findViewById(R.id.bidbtn);
                sp = itemView.findViewById(R.id.sprp);
                mrp = itemView.findViewById(R.id.mrprp);
                time = itemView.findViewById(R.id.time);
                image = itemView.findViewById(R.id.image_view);
                name = itemView.findViewById(R.id.name);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return 1;
        }

    }

    void initRecycler(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewCurrent = findViewById(R.id.recyclerviewCurrent);
        recyclerViewCurrent.setLayoutManager(layoutManager);
        RecyclerViewAdapterCurrent adapter = new RecyclerViewAdapterCurrent(this, current_products);
        recyclerViewCurrent.setAdapter(null);
        recyclerViewCurrent.setAdapter(adapter);
        TextView mrp = (TextView) findViewById(R.id.mrpproduct);
        mrp.setText(current_products.get(position1).getMrp());
        TextView sellingprice = (TextView) findViewById(R.id.sellingPrice);
        title.setText(current_products.get(position1).getTitle());
        sellingprice.setText(current_products.get(position1).getSp());
        TextView endDate = (TextView) findViewById(R.id.endDate);
        endDate.setText(current_products.get(position1).getEnd_date());
        Glide.with(this)
                .asBitmap()
                .load(current_products.get(position1).getImage_url())
                .into(imageView);

    }
}
