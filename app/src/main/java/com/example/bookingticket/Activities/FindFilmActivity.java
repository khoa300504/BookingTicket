package com.example.bookingticket.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookingticket.Adapters.FilmSearchAdapter;
import com.example.bookingticket.Domain.FilmItem;
import com.example.bookingticket.Domain.SearchResult;
import com.example.bookingticket.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class FindFilmActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFilms;
    private StringRequest mStringRequest;
    private FilmSearchAdapter filmSearchAdapter;
    private RequestQueue mRequestQueue;
    private EditText findFilmValue;
    private ImageView findFilmImg, backImg;
    private ProgressBar loading;
    private String filmFind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_film);
        filmFind = getIntent().getStringExtra("findValue");

        initView();
        sendRequestSearchMovie(filmFind);
    }

    private void sendRequestSearchMovie(String filmFind) {
        mRequestQueue = Volley.newRequestQueue(this);
        System.out.println(filmFind);
        loading.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.GET, "https://booking-app-ylnj.onrender.com/v1/movies/search/" + filmFind, response -> {
            Log.d("KhoaNguyenDev", "Response: " + response);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<FilmItem>>() {}.getType();
            List<FilmItem> items = gson.fromJson(response, listType);
            loading.setVisibility(View.GONE);
            filmSearchAdapter = new FilmSearchAdapter(items);
            recyclerViewFilms.setAdapter(filmSearchAdapter);
        }, error -> {
            loading.setVisibility(View.GONE);
            Log.i("KhoaNguyenDev",  "onErrorReponse: " +error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

    private void initView() {
        recyclerViewFilms = findViewById(R.id.recyclerViewFilms);
        recyclerViewFilms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        loading = findViewById(R.id.loading);
        findFilmValue = findViewById(R.id.findFilmValue);
        findFilmImg = findViewById(R.id.findFilmImg);
        findFilmImg.setOnClickListener(v -> {
            sendRequestSearchMovie(String.valueOf(findFilmValue.getText()));
        });
        backImg = findViewById(R.id.backImg);
        backImg.setOnClickListener(v -> {
            finish();
        });
    }
}
