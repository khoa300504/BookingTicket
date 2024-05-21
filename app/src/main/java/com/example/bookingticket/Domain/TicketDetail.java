
package com.example.bookingticket.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketDetail {

    @SerializedName("movieId")
    @Expose
    private Integer movieId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;
    @SerializedName("seat")
    @Expose
    private Integer seat;
    @SerializedName("title")
    @Expose
    private String title;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
