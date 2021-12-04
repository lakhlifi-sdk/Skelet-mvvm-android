package com.example.skelet_android_mvvm.datamanager.retrofit;




import com.example.skelet_android_mvvm.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient restService;
    private RestEndpoint restEndpoint;

    private RetrofitClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(interceptor);
        client.connectTimeout(30, TimeUnit.SECONDS);
        client.readTimeout(30, TimeUnit.SECONDS);
        client.writeTimeout(30, TimeUnit.SECONDS);

        client.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
                    //.addHeader("Accept", "application/json;version=" + BuildConfig.VERSION_NAME)
                    //.addHeader("os", "android");
                    //.addHeader("Authorization", ApiUrls.AUTHORIZATION);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });




        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASEURL)
                .client(client.build())
                .build();
        restEndpoint = retrofit.create(RestEndpoint.class);
    }

    public static RetrofitClient getInstance() {
        if (restService == null) {
            restService = new RetrofitClient();
        }
        return restService;
    }

    public RestEndpoint endpoint() {
        return restEndpoint;
    }
}
