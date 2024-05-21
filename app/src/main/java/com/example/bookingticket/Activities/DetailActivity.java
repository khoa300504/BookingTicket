package com.example.bookingticket.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bookingticket.Adapters.CategoryEachFilmListAdapter;
import com.example.bookingticket.Domain.FilmItem;
import com.example.bookingticket.R;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt, movieRateTxt, movieTimeTxt, movieSumaryInfo, movieActorInfo;
    private int idFilm;
    private ImageView pic2, backImg;
    private RecyclerView.Adapter adapterCategory;
    private RecyclerView recyclerViewCategory;
    private NestedScrollView scrollView;

    Context context;

    private Button bookingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        idFilm = getIntent().getIntExtra("id", 1);

        bookingButton = findViewById(R.id.button);

        bookingButton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, BookingActivity.class);
            intent.putExtra("id", idFilm);
            startActivity(intent);
        });

        initView();
        sendRequest();
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        mStringRequest = new StringRequest(Request.Method.GET, "https://booking-app-ylnj.onrender.com/v1/movies/" + idFilm, response -> {
            Gson gson = new Gson();
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);

            FilmItem item = gson.fromJson(response, FilmItem.class);

            Glide.with(DetailActivity.this)
                    .load(item.getPoster())
                    .into(pic2);

            titleTxt.setText(item.getTitle());
            movieRateTxt.setText(item.getImdbRating());
            movieSumaryInfo.setText(item.getPlot());
            movieTimeTxt.setText(item.getRuntime());
            movieActorInfo.setText(item.getActors());
            if(item.getGenres()!=null)
            {
                adapterCategory = new CategoryEachFilmListAdapter(item.getGenres());
                recyclerViewCategory.setAdapter(adapterCategory);
            }
        }, error -> progressBar.setVisibility(View.GONE));
        mRequestQueue.add(mStringRequest);
    }

    private void initView() {
        titleTxt = findViewById(R.id.movieNameTxt);
        progressBar = findViewById(R.id.progressBarDetail);
        scrollView = findViewById(R.id.NestedScrollView);
        pic2 = findViewById(R.id.picDetail);
        movieRateTxt = findViewById(R.id.movieStar);
        movieActorInfo = findViewById(R.id.movieActorInfo);
        movieSumaryInfo = findViewById(R.id.movieSummary);
        movieTimeTxt = findViewById(R.id.movieTime);
        backImg = findViewById(R.id.backImg);
        recyclerViewCategory = findViewById(R.id.genreView);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        backImg.setOnClickListener(v -> finish());
    }
}