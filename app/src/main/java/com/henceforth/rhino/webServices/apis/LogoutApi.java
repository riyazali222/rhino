package com.henceforth.rhino.webServices.apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by All Mankind on 3/21/2017.
 */
public class LogoutApi {
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }
}
