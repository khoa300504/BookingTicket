
package com.example.bookingticket.Domain;

import java.util.List;

public class Genre {

    private List<GenresItem> genres;

    public List<GenresItem> getGenres() {
        return genres;
    }

    public void setGenres(List<GenresItem> genres) {
        this.genres = genres;
    }
}
