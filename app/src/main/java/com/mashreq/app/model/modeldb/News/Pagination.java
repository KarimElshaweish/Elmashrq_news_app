package com.mashreq.app.model.modeldb.News;

import com.google.gson.annotations.SerializedName;

public class Pagination {
    @SerializedName("total")
    String total;
    @SerializedName("lastPage")
    String lastPage;
    @SerializedName("perPage")
    String perPage;
    @SerializedName("currentPage")
    String currentPage;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getLastPage() {
        return lastPage;
    }

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    public String getPerPage() {
        return perPage;
    }

    public void setPerPage(String perPage) {
        this.perPage = perPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}
