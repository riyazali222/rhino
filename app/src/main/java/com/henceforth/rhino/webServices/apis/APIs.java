package com.henceforth.rhino.webServices.apis;

import com.henceforth.rhino.utills.ApiList;
import com.henceforth.rhino.utills.ForgotPasswordApi;
import com.henceforth.rhino.webServices.pojo.ItemsList;
import com.henceforth.rhino.webServices.pojo.NotificationsLists;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIs {


    @POST("api/v1/login")
    Call<ApiList> loginResponse(@Query("usercred") String usercred,
                                @Query("password") String password,
                                @Query("device_type") String device_type,
                                @Query("device_id") String device_id,
                                @Query("fcm_id") String fcm_id);


    @POST("api/v1/forgot-password")
    Call<ForgotPasswordApi> forgotPasswordResponse(@Query("email") String email);


    @GET("api/v1/vehicle-listing")
    Call<List<ItemsList>> VehicleListResponse();


    @POST("api/v1/request-service")
    Call<ApiList> requestServicesResponse(@Query("license_plate_no") String license_plate_no,
                                          @Query("vehicle_mileage") String vehicle_mileage,
                                          @Query("type_of_vehicle") String type_of_vehicle,
                                          @Query("request_type") String request_type,
                                          @Query("vehicle_model") String vehicle_model,
                                          @Query("vehicle_year") String vehicle_year,
                                          @Query("phone_no") String phone_no,
                                          @Query("lat") double lat,
                                          @Query("lng") double lng);

    @GET("api/v1/notification-listing")
    Call<List<NotificationsLists>> NotificationListResponse();


    @GET("api/v1/set-fcm-id")
    Call<ApiList> getFcmIdResponce(@Query("device_type") String device_type,
                                   @Query("device_id") String device_id,
                                   @Query("fcm_id") String fcm_id);

}



