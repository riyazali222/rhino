package com.henceforth.rhino.utills;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ForgotPasswordApi {

    @SerializedName("message")
    @Expose
    private String message;

    public String getmessage() {
        return message;
    }


}
