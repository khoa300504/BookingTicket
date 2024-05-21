
package com.example.bookingticket.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Seat {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("state")
    @Expose
    private Boolean state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

}
