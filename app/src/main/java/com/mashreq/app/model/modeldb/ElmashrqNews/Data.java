package com.mashreq.app.model.modeldb.ElmashrqNews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mashreq.app.model.modeldb.Cities.City;

import java.util.List;

public class Data {
    @SerializedName("latest_news")
    @Expose
    private List<LatestNews> latestNews = null;
    @SerializedName("most_read")
    @Expose
    private List<LatestNews> mostRead = null;
    @SerializedName("agel_news")
    @Expose
    private List<LatestNews> agelNews = null;
    @SerializedName("cities")
    @Expose
    private List<City> cities = null;

    public List<LatestNews> getLatestNews() {
        return latestNews;
    }

    public void setLatestNews(List<LatestNews> latestNews) {
        this.latestNews = latestNews;
    }

    public List<LatestNews> getMostRead() {
        return mostRead;
    }

    public void setMostRead(List<LatestNews> mostRead) {
        this.mostRead = mostRead;
    }

    public List<LatestNews> getAgelNews() {
        return agelNews;
    }

    public void setAgelNews(List<LatestNews> agelNews) {
        this.agelNews = agelNews;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}
