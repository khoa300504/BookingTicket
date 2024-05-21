package com.example.bookingticket.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult {

    @SerializedName("films")
    @Expose
    private List<FilmItem> films;

    public SearchResult(List<FilmItem> films) {
        this.films = films;
    }

    public List<FilmItem> getFilms() {
        return films;
    }

    public void setFilms(List<FilmItem> films) {
        this.films = films;
    }

    public int getItemCount() {
        return films != null ? films.size() : 0;
    }
}
