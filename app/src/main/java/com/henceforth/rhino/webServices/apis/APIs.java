package com.henceforth.rhino.webServices.apis;

import com.henceforth.rhino.utills.AddVehicles;
import com.henceforth.rhino.utills.ApiList;
import com.henceforth.rhino.utills.ForgotPasswordApi;
import com.henceforth.rhino.webServices.Services;
import com.henceforth.rhino.webServices.pojo.EditProfile;
import com.henceforth.rhino.webServices.pojo.ItemsList;
import com.henceforth.rhino.webServices.pojo.NotificationsLists;
import com.henceforth.rhino.webServices.pojo.RemoveVehicles;
import com.henceforth.rhino.webServices.pojo.VehicleListing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface APIs {


    @POST("paragon/public/api/v1/login")
    Call<ApiList> loginResponse(@Query("usercred") String usercred,
                                @Query("password") String password,
                                @Query("device_type") String device_type,
                                @Query("device_id") String device_id,
                                @Query("fcm_id") String fcm_id);


    @POST("api/v1/forgot-password")
    Call<ForgotPasswordApi> forgotPasswordResponse(@Query("email") String email);


    @GET("paragon/public/api/v1/vehicle-makes")
    Call<List<ItemsList>> VehicleListResponse();


    @POST("paragon/public/api/v1/request-service")
    Call<ApiList> requestServicesResponse(@Query("license_plate_no") String license_plate_no,
                                          @Query("vehicle_mileage") String vehicle_mileage,
                                          @Query("type_of_vehicle") String type_of_vehicle,
                                          @Query("service_id") String service_id,
                                          @Query("vehicle_make_id") String vehicle_make_id,
                                          @Query("vehicle_model") String vehicle_model,
                                          @Query("vehicle_year") String vehicle_year,
                                          @Query("phone_no") String phone_no,
                                          @Query("lat") double lat,
                                          @Query("lng") double lng,
                                          @Query("current_odometer_reading") String
                                                  current_odometer_reading,
                                          @Query("not_for_you") String not_for_you,
                                          @Query("description") String description
                                          );

    @GET("paragon/public/api/v1/notification-listing")
    Call<List<NotificationsLists>> NotificationListResponse();


    @POST("paragon/public/api/v1/logout")
    Call<LogoutApi> LogoutResponse(@Query("device_id") String device_id);

    @PUT("api/v1/change-password")
    Call<ChangePasswordApi> ChangePasswordResponse(@Query("old_password") String old_password,
                                                   @Query("new_password") String new_password);

    @POST("paragon/public/api/v1/notification-enable-disable")
    Call<EnableDisableApi> EnableDisableNotification(@Query("device_id") String device_id,
                                                     @Query("status") String status);


    @POST("paragon/public/api/v1/save-vehicle")
    Call<AddVehicles> AddVehicleApi(@Query("user_vehicle_id") String user_vehicle_id,
                                       @Query("license_plate_no") String license_plate_no,
                                       @Query("vehicle_identification_number")
                                               String vehicle_identification_number,
                                       @Query("membership_id") String membership_id,
                                       @Query("vehicle_mileage") String vehicle_mileage,
                                       @Query("type_of_vehicle") String type_of_vehicle,
                                       @Query("vehicle_make_id") String vehicle_make_id,
                                       @Query("vehicle_model") String vehicle_model,
                                       @Query("vehicle_year") Integer vehicle_year
    );


    @POST("paragon/public/api/v1/remove-vehicle")
    Call<RemoveVehicles> DeleteVehiclesResponse(@Query("user_vehicle_id") String user_vehicle_id,
                                                @Query("_method") String _method);


    @GET("paragon/public/api/v1/vehicle-listing")
    Call<List<VehicleListing>> VehicleListingResponse(@Header("Authorization") String sessionId);

   /* @Multipart
    @POST("paragon/public/api/v1/edit-profile")
    Call<EditProfile> editProfile(@Header("authorization") String authorization, @PartMap HashMap<String, RequestBody> hashMap);

    @Multipart
    @POST("paragon/public/api/v1/edit-profile")
    Call<EditProfile> editProfileWithImage(@Header("authorization") String authorization
            , @PartMap HashMap<String, RequestBody> hashMap, @Part MultipartBody.Part profilePic);*/

    @Multipart
    @POST("paragon/public/api/v1/edit-profile")
    Call<EditProfile> editProfileWithImage(@Part("firstname") RequestBody firstname,
                                          @Part MultipartBody.Part image,
                                          @Part("phone_no") RequestBody phone_no,
                                          @Part("lastname") RequestBody lastname,
                                          @Part("middlename") RequestBody middlename,
                                          @Part("company_name") RequestBody company_name,
                                          @Part("address1") RequestBody address1,
                                          @Part("address2") RequestBody address2,
                                          @Part("address3") RequestBody address3,
                                          @Part("city") RequestBody city,
                                          @Part("state") RequestBody state,
                                          @Part("country") RequestBody country);

    @Multipart
    @POST("paragon/public/api/v1/edit-profile")
    Call<EditProfile> editProfile(@Part("firstname") RequestBody firstname,
                                           @Part("phone_no") RequestBody phone_no,
                                           @Part("lastname") RequestBody lastname,
                                           @Part("middlename") RequestBody middlename,
                                           @Part("company_name") RequestBody company_name,
                                           @Part("address1") RequestBody address1,
                                           @Part("address2") RequestBody address2,
                                           @Part("address3") RequestBody address3,
                                           @Part("city") RequestBody city,
                                           @Part("state") RequestBody state,
                                           @Part("country") RequestBody country);
    @GET("paragon/public/api/v1/service-types")
    Call<List<Services>> ServiceListResponse();
}



