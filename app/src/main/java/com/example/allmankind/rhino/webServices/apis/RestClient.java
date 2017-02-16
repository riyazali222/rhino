package com.example.allmankind.rhino.webServices.apis;

import com.example.allmankind.rhino.utills.ApplicationGlobal;
import com.example.allmankind.rhino.utills.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {


    private static APIs REST_CLIENT;
    private static Retrofit retrofit;

    static {
        setupRestClient();
    }

    private RestClient() {
    }

    public static APIs get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        REST_CLIENT = retrofit.create(APIs.class);
    }


    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(300, TimeUnit.SECONDS);
        httpClient.readTimeout(300, TimeUnit.SECONDS);
        // add your other interceptors …

// add logging as last interceptor
        httpClient.addNetworkInterceptor(logging);
        //add interceptor for header
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", ApplicationGlobal.sessionId); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }

    public static Retrofit getRetrofitInstance() {
        return retrofit;
    }

}

