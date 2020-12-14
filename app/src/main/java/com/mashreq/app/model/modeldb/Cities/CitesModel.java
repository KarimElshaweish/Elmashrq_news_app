package com.mashreq.app.model.modeldb.Cities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CitesModel {
    @SerializedName("status")
    String statues;
    @SerializedName("data")
    List<City>data;
    @SerializedName("message")
    String message;


    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }

    public List<City> getData() {
        return data;
    }

    public void setData(List<City> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
