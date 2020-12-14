package com.mashreq.app.model.modeldb.SingleNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("new")
    @Expose
    private New _new;
    @SerializedName("related_news")
    @Expose
    private List<RelatedNews> relatedNews = null;

    public New getNew() {
        return _new;
    }

    public void setNew(New _new) {
        this._new = _new;
    }

    public List<RelatedNews> getRelatedNews() {
        return relatedNews;
    }

    public void setRelatedNews(List<RelatedNews> relatedNews) {
        this.relatedNews = relatedNews;
    }
}
