package com.example.bookingticket.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookingticket.Domain.FilmTicket;
import com.example.bookingticket.Domain.Seat;
import com.example.bookingticket.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BookingActivity extends AppCompatActivity {

    private int idFilm, seatNum;
    private String fullname, dateTime;
    private RequestQueue mRequestQueue;

    private ImageView backImg;

    private Spinner spinnerTime;
    private GridLayout gridLayoutSeats;
    private TextView ticketPriceValue;

    private Button buttonComplete;

    private EditText editFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        buttonComplete = findViewById(R.id.buttonComplete);
        buttonComplete.setOnClickListener(v -> {
            createTicketDetail();
        });

        idFilm = getIntent().getIntExtra("id", 1);
        initView();
        sendRequest();
    }

    private void initView() {
        spinnerTime = findViewById(R.id.spinnerTime);
        gridLayoutSeats = findViewById(R.id.gridLayoutSeats);
        ticketPriceValue = findViewById(R.id.ticketPriceValue);
        backImg = findViewById(R.id.backImg);
        editFullName = findViewById(R.id.editFullName);
        editFullName.addTextChangedListener(textWatcher);
        backImg.setOnClickListener(v -> finish());

        // Đảm bảo `dateTime` được cập nhật khi người dùng chọn một mục từ spinner
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dateTime = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì cả
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            fullname = editFullName.getText().toString();
        }
    };

    private void createTicketDetail() {
        if (fullname == null || fullname.isEmpty() || dateTime == null || seatNum == 0) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mRequestQueue = Volley.newRequestQueue(this);
        String url = "https://booking-app-ylnj.onrender.com/v1/tickets/create";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("movieId", idFilm);
            jsonBody.put("fullname", fullname);
            jsonBody.put("dateTime", dateTime);
            jsonBody.put("seat", seatNum);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating JSON body", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonBody,
                response -> {
                    showSuccessMessage();
                    Intent intent = new Intent(BookingActivity.this, MainActivity.class);
                    startActivity(intent);
                },
                error -> {
                    Log.e("BookingActivity", "Error creating ticket", error);
                    Toast.makeText(this, "Error creating ticket", Toast.LENGTH_SHORT).show();
                });

        mRequestQueue.add(jsonObjectRequest);
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        String url = "https://booking-app-ylnj.onrender.com/v1/movies/ticket/" + idFilm;
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Gson gson = new Gson();
            FilmTicket item = gson.fromJson(response, FilmTicket.class);
            updateUI(item);
        }, error -> Log.e("BookingActivity", "Error fetching data", error));

        mRequestQueue.add(mStringRequest);
    }

    private void updateUI(FilmTicket filmTicket) {
        // Update spinner with dateTime
        List<String> dateTimeList = filmTicket.getDateTime();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, dateTimeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapter);

        gridLayoutSeats.removeAllViews();
        for (Seat seat : filmTicket.getSeat()) {
            Button seatButton = new Button(this);
            seatButton.setText(String.valueOf(seat.getId()));
            seatButton.setEnabled(!seat.getState());
            seatButton.setTextColor(Color.parseColor("#dfe6e9"));
            seatButton.setBackgroundTintList(getResources().getColorStateList(R.color.button_background, getTheme()));


            seatButton.setOnClickListener(v -> {
                seatNum = seat.getId();
                for (int i = 0; i < gridLayoutSeats.getChildCount(); i++) {
                    Button button = (Button) gridLayoutSeats.getChildAt(i);
                    if (button.isEnabled()) {
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#81ecec")));
                    }
//                    button.setBackgroundTintList(getResources().getColorStateList(R.color.button_background, getTheme()));
                }
                seatButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00b894")));
            });

            gridLayoutSeats.addView(seatButton);
        }

        // Update ticket price
        ticketPriceValue.setText(String.format("%dđ", filmTicket.getPrice()));
    }

    private void showSuccessMessage() {
        Context context = getApplicationContext();
        CharSequence message = "Đặt vé thành công!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
