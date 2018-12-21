package com.example.apple.auctionproject.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.apple.auctionproject.model.Current_Product;
import com.example.apple.auctionproject.model.Past_Products;
import com.example.apple.auctionproject.R;
import com.example.apple.auctionproject.adapter.RecyclerViewAdapterPast;
import com.example.apple.auctionproject.model.Upcoming_Products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<Current_Product> current_products = new ArrayList<>();
    public ArrayList<Past_Products> past_products = new ArrayList<>();
    public ArrayList<Upcoming_Products> upcoming_products = new ArrayList<>();
    TextView heading;
    Dialog dialog;
    Context context = this;
    FloatingActionButton fab;
    int backPressed = 0;

    @Override
    public void onBackPressed() {
        backPressed++;
        if(backPressed == 1)
            Toast.makeText(this,"Press again to exit", Toast.LENGTH_SHORT).show();
        if(backPressed == 2){
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView noOfBids;

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyCart.class));
            }
        });

        noOfBids = (TextView) findViewById(R.id.numberOfBids);
        noOfBids.setText(0 + "");

        heading = (TextView) findViewById(R.id.heading);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/robotofont.ttf");
        heading.setTypeface(type);
        BottomAppBar b = (BottomAppBar) findViewById(R.id.bar);
        setSupportActionBar(b);
        b.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        loadPastProducts();
        loadCurrentProducts();

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.bid_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //loadUpcomingProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottomappbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.wallet:
                startActivity(new Intent(MainActivity.this, WalletActivity.class));
                break;
            case R.id.profile:
                startActivity(new Intent(MainActivity.this, Profile.class));
                break;
        }
        return super.onOptionsItemSelected(item);
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
                            initRecyclerView();
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

    void loadUpcomingProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://easyvela.esy.es/AndroidAPI/upcomingproducts.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray heroArray = new JSONArray(response);
                            Log.d("response", response);
                            Log.d("length", heroArray.length() + "");
                            for (int i = 0; i < heroArray.length(); i++) {
                                JSONObject heroObject = heroArray.getJSONObject(i);
                                Upcoming_Products c = new Upcoming_Products(heroObject.getString("upcoming_id"),
                                        heroObject.getString("image_url"),
                                        heroObject.getString("title"),
                                        heroObject.getString("endtime"),
                                        heroObject.getString("mrp"),
                                        heroObject.getString("sp"));
                                upcoming_products.add(c);
                            }
                            initRecyclerView();
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

    void loadPastProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://easyvela.esy.es/AndroidAPI/pastproducts.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            past_products.clear();
                            JSONArray heroArray = new JSONArray(response);
                            Log.d("length", heroArray.length() + "");
                            for (int i = 0; i < heroArray.length(); i++) {
                                JSONObject heroObject = heroArray.getJSONObject(i);
                                Past_Products c = new Past_Products(heroObject.getString("past_id"),
                                        heroObject.getString("image_url"),
                                        heroObject.getString("title"),
                                        heroObject.getString("endtime"),
                                        heroObject.getString("mrp"),
                                        heroObject.getString("sp"));
                                past_products.add(c);
                            }
                            initRecyclerView();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void initRecyclerView() {

        //Current Products
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewCurrent = findViewById(R.id.recyclerviewCurrent);
        recyclerViewCurrent.setLayoutManager(layoutManager);
        RecyclerViewAdapterCurrent adapter = new RecyclerViewAdapterCurrent(this, current_products);
        recyclerViewCurrent.setAdapter(null);
        recyclerViewCurrent.setAdapter(adapter);

        //Upcoming products
//        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        RecyclerView recyclerViewUp = findViewById(R.id.recyclerviewUpcoming);
//        recyclerViewUp.setLayoutManager(layoutManager1);
//        RecyclerViewAdapterUpcoming adapter1 = new RecyclerViewAdapterUpcoming(this, upcoming_products);
//        recyclerViewUp.setAdapter(null);
//        recyclerViewUp.setAdapter(adapter1);

        //Past Products
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewPast = findViewById(R.id.recyclerviewPast);
        recyclerViewPast.setLayoutManager(layoutManager2);
        RecyclerViewAdapterPast adapter2 = new RecyclerViewAdapterPast(this, past_products);
        recyclerViewPast.setAdapter(null);
        recyclerViewPast.setAdapter(adapter2);
    }

    class RecyclerViewAdapterCurrent extends RecyclerView.Adapter<RecyclerViewAdapterCurrent.ViewHolder> {

        public int position;
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

            this.position = position;

            holder.setIsRecyclable(false);

            date = current_products.get(position).getEnd_date();

            Log.d("dates : ", date + "");

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
                                    initRecyclerView();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                    requestQueue.add(stringRequest);
                }
            }.start();

            holder.bid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView basePrice = (TextView) dialog.findViewById(R.id.basePriceValue);
                    final TextView totalPrice = (TextView) dialog.findViewById(R.id.totalPriceValue);
                    TextView title = (TextView) dialog.findViewById(R.id.dialogTitle);
                    basePrice.setText("₹ " + current_products.get(position).getSp());
                    title.setText(current_products.get(position).getTitle());

                    int total = 0;

                    final int bp = Integer.parseInt(current_products.get(position).getSp());
                    EditText myBid = (EditText) dialog.findViewById(R.id.bidAmount);
                    dialog.show();

                    final TextWatcher mTextEditorWatcher = new TextWatcher() {
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            //This sets a textview to the current length
                            int sum = 0;

                            if (!s.equals("")) {
                                try {
                                    sum = Integer.parseInt(s.toString()) + bp;
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                                totalPrice.setText("₹ " + sum);
                            }

                        }

                        public void afterTextChanged(Editable s) {
                        }
                    };

                    myBid.addTextChangedListener(mTextEditorWatcher);
                }
            });


            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, Product_Description.class);
                    i.putExtra("id", position + "");
                    Log.d("Position", position + "");
                    startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return current_products.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

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

}