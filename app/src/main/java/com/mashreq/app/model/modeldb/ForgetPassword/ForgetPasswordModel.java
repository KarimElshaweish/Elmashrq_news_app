package com.mashreq.app.model.modeldb.ForgetPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgetPasswordModel {
    @SerializedName("status")
    @Expose()
    String status;

    @SerializedName("message")
    @Expose()
    String message;

    @SerializedName("data")
    @Expose()
    ForgetPasswordData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ForgetPasswordData getData() {
        return data;
    }

    public void setData(ForgetPasswordData data) {
        this.data = data;
    }
}
