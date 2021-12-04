package com.example.skelet_android_mvvm.datamanager.retrofit;
import com.example.skelet_android_mvvm.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestEndpoint {

    @GET(ApiEndpoints.POST_URL)
    Call<List<Post>> getPosts();

}
