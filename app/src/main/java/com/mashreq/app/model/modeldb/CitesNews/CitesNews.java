package com.mashreq.app.model.modeldb.CitesNews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mashreq.app.model.modeldb.ElmashrqNews.LatestNews;

import java.util.List;

public class CitesNews {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<LatestNews> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<LatestNews> getData() {
        return data;
    }

    public void setData(List<LatestNews> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
