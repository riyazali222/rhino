package com.example.allmankind.rhino.webServices;

import com.example.allmankind.rhino.utills.ApiList;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIs {


    @POST("api/v1/login")
    Call<ApiList> loginResponse(@Query("usercred") String usercred,
                                @Query("password") String password, @Query("device_type") String device_type,
                                @Query("device_id") String device_id, @Query("fcm_id") String fcm_id);

    /*@Multipart
    @POST("api/v1/signup")
    Call<SignUp> signUpResponse(@Part("username") String username, @Part("email") String email,
                                @Part("password") String password, @Part("name") String name,
                                @Part MultipartBody.Part image, @Part("phone_no") String phone_no,
                                @Part("city") String city, @Part("gender") String gender,
                                @Part("dob") String dob,
                                @Part("about") String about, @Part("website_link") String website_link);*/
    @POST("api/v1/login")
    Call<ApiList> forgotPasswordResponse(@Query("email") String email);
}



