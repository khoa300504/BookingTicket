package com.example.bookingticket.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookingticket.Adapters.TicketAdapter;
import com.example.bookingticket.Domain.TicketDetail;
import com.example.bookingticket.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TicketHistoryActivity extends AppCompatActivity {

    private RecyclerView ticketRecyclerView;
    private TicketAdapter ticketAdapter;
    private ProgressBar loading;
    private RequestQueue mRequestQueue;
    private ImageView imageView2;
    private TextView textView5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_history);

        ticketRecyclerView = findViewById(R.id.ticketRecyclerView);
        loading = findViewById(R.id.loading);

        ticketRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRequestQueue = Volley.newRequestQueue(this);
        fetchTickets();
        initView();
    }

    private void initView() {
        imageView2 = findViewById(R.id.imageView2);
        textView5 = findViewById(R.id.textView5);
        imageView2.setOnClickListener(v -> {
            Intent intent = new Intent(TicketHistoryActivity.this, MainActivity.class);
            startActivity(intent);
        });
        textView5.setOnClickListener(v -> {
            Intent intent = new Intent(TicketHistoryActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void fetchTickets() {
        String url = "https://booking-app-ylnj.onrender.com/v1/tickets/";
        loading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    loading.setVisibility(View.GONE);
                    Type listType = new TypeToken<List<TicketDetail>>() {}.getType();
                    List<TicketDetail> ticketList = new Gson().fromJson(response, listType);
                    ticketAdapter = new TicketAdapter(ticketList);
                    ticketRecyclerView.setAdapter(ticketAdapter);
                },
                error -> {
                    loading.setVisibility(View.GONE);
                    Log.e("TicketHistoryActivity", "Error fetching tickets", error);
                });

        mRequestQueue.add(stringRequest);
    }
}
