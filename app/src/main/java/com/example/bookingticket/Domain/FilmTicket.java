
package com.example.bookingticket.Domain;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilmTicket {

    @SerializedName("movieId")
    @Expose
    private Integer movieId;
    @SerializedName("dateTime")
    @Expose
    private List<String> dateTime;
    @SerializedName("seat")
    @Expose
    private List<Seat> seat;
    @SerializedName("price")
    @Expose
    private Integer price;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public List<String> getDateTime() {
        return dateTime;
    }

    public void setDateTime(List<String> dateTime) {
        this.dateTime = dateTime;
    }

    public List<Seat> getSeat() {
        return seat;
    }

    public void setSeat(List<Seat> seat) {
        this.seat = seat;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
