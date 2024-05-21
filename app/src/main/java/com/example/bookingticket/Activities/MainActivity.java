package com.example.bookingticket.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookingticket.Adapters.CategoryListAdapter;
import com.example.bookingticket.Adapters.FilmListAdapter;
import com.example.bookingticket.Adapters.SliderAdapters;
import com.example.bookingticket.Domain.GenresItem;
import com.example.bookingticket.Domain.ListFilm;
import com.example.bookingticket.Domain.SliderItems;
import com.example.bookingticket.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterBestMovies, adapterUpComming, adapterCategory;
    private RecyclerView recyclerViewBestMovies;
    private RecyclerView recyclerViewUpComming;
    private RecyclerView recyclerViewCategory;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2, mStringRequest3;
    private ProgressBar loading1, loading2, loading3;
    private ViewPager2 viewPager2;
    private ImageView findFilmImg;
    private EditText findFilmValue;
    private String filmFind;
    private Handler slideHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListeners();
        banners();
        sendRequestBestMovies();
        sendRequestUpComming();
        sendRequestCatogory();
    }

    private void initListeners() {
        findViewById(R.id.imageView3).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TicketHistoryActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.textView6).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TicketHistoryActivity.class);
            startActivity(intent);
        });
    }

    private void sendRequestBestMovies() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.GET, "https://booking-app-ylnj.onrender.com/v1/movies/?page=1", response -> {
            Gson gson = new Gson();
            loading1.setVisibility(View.GONE);
            ListFilm items=gson.fromJson(response, ListFilm.class);
            adapterBestMovies = new FilmListAdapter(items);
            recyclerViewBestMovies.setAdapter(adapterBestMovies);
        }, error -> {
            loading1.setVisibility(View.GONE);
            Log.i("KhoaNguyenDev",  "onErrorReponse: " +error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

    private void sendRequestUpComming() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading3.setVisibility(View.VISIBLE);
        mStringRequest3 = new StringRequest(Request.Method.GET, "https://booking-app-ylnj.onrender.com/v1/movies/?page=2", response -> {
            Gson gson = new Gson();
            loading3.setVisibility(View.GONE);
            ListFilm items=gson.fromJson(response, ListFilm.class);
            adapterUpComming = new FilmListAdapter(items);
            recyclerViewUpComming.setAdapter(adapterUpComming);
        }, error -> {
            loading3.setVisibility(View.GONE);
            Log.i("KhoaNguyenDev",  "onErrorReponse: " +error.toString());
        });
        mRequestQueue.add(mStringRequest3);
    }

    private void sendRequestCatogory() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading2.setVisibility(View.VISIBLE);
        mStringRequest2 = new StringRequest(Request.Method.GET, "https://booking-app-ylnj.onrender.com/v1/movies/genres", response -> {
            Gson gson = new Gson();
            loading2.setVisibility(View.GONE);
            ArrayList<GenresItem> catList = gson.fromJson(response, new TypeToken<ArrayList<GenresItem>>(){
            }.getType());
            adapterCategory = new CategoryListAdapter(catList);
            recyclerViewCategory.setAdapter(adapterCategory);
        }, error -> {
            loading2.setVisibility(View.GONE);
            Log.i("KhoaNguyenDev",  "onErrorReponse: " +error.toString());
        });
        mRequestQueue.add(mStringRequest2);
    }

    private void banners() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.ad1));
        sliderItems.add(new SliderItems(R.drawable.ad2));
        sliderItems.add(new SliderItems(R.drawable.ad3));

        viewPager2.setAdapter(new SliderAdapters(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r= 1 - Math.abs(position);
            page.setScaleY(0.85f+r*0.15f);
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    protected void onResume(){
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 2000);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            filmFind = findFilmValue.getText().toString();
        }
    };

    private void initView() {
        viewPager2 = findViewById(R.id.viewpagerSlider);
        recyclerViewBestMovies = findViewById(R.id.view1);
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpComming = findViewById(R.id.view3);
        recyclerViewUpComming.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory = findViewById(R.id.view2);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loading1 = findViewById(R.id.progressBar1);
        loading2 = findViewById(R.id.progressBar2);
        loading3 = findViewById(R.id.progressBar3);
        findFilmValue = findViewById(R.id.findFilmValue);
        findFilmValue.addTextChangedListener(textWatcher);
        findFilmImg = findViewById(R.id.findFilmImg);
        findFilmImg.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FindFilmActivity.class);
            intent.putExtra("findValue", filmFind);
            findFilmValue.setText("");
            startActivity(intent);
        });
    }
}