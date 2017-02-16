package com.example.allmankind.rhino.webServices.apis;

import com.example.allmankind.rhino.utills.ApiList;
import com.example.allmankind.rhino.webServices.pojo.ItemsList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    @GET("api/v1/vehicle-listing")
    Call<List<ItemsList>> VehicleListResponse();


    @POST("api/v1/request-service")
    Call<ApiList> requestServicesResponse(@Query("vendor_id") String vendor_id,
                                          @Query("product_id") String product_id,
                                          @Query("service_id") String service_id,
                                          @Query("vehicle_id") String vehicle_id,
                                          @Query("name") String name,
                                          @Query("license_plate_no") String license_plate_no,
                                          @Query("vehicle_mileage") String vehicle_mileage,
                                          @Query("type_of_vehicle") String type_of_vehicle,
                                          @Query("request_type") String request_type,
                                          @Query("vehicle_make_id") String vehicle_make_id,
                                          @Query("vehicle_model") String vehicle_model,
                                          @Query("vehicle_year") String vehicle_year,
                                          @Query("phone_no") String phone_no,
                                          @Query("location") String location);

}



