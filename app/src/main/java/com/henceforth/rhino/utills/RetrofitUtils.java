package com.henceforth.rhino.utills;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class RetrofitUtils {

    public static RequestBody stringToRequestBody(String string) {
        return RequestBody.create(
                MediaType.parse("multipart/form-data"), string);
    }

    public static MultipartBody.Part imageToRequestBody(File file, String fieldName) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        return MultipartBody.Part.createFormData(fieldName, file.getName(), requestFile);
    }

}

