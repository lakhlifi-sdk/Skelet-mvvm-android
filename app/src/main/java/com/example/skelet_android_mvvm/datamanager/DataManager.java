package com.example.skelet_android_mvvm.datamanager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.skelet_android_mvvm.datamanager.retrofit.RetrofitClient;
import com.example.skelet_android_mvvm.models.Post;
import com.example.skelet_android_mvvm.utils.ApiError;
import com.example.skelet_android_mvvm.utils.Resource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataManager {
    String connexionMessage ="Connexion réseau indisponible. Assurez-vous que votre connexion réseau est active et réessayez.";
    private void handleThrowableException(Throwable e, MutableLiveData mutableLiveData) {
        if (e instanceof UnknownHostException || e instanceof ConnectException || e instanceof SocketTimeoutException) {
            mutableLiveData.setValue(Resource.error(connexionMessage, null));
        } else {
            mutableLiveData.setValue(Resource.error(e.getMessage(), null));
        }
    }



    public void getPosts(MutableLiveData<Resource<List<Post>>> mutableLiveData) {
        Call<List<Post>> call = RetrofitClient.getInstance().endpoint().getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        mutableLiveData.setValue(Resource.success(response.body()));
                    }
                } else {

                    if (response.code() == 331) {

                        mutableLiveData.setValue(Resource.invalidToken(null));

                    } else {
                        mutableLiveData.setValue(Resource.error("message error", null));
                        /*Gson gson = new GsonBuilder().create();
                        ApiError mError;
                        try {
                            mError = gson.fromJson(response.errorBody().string(), ApiError.class);
                            mutableLiveData.setValue(Resource.error(mError.getMessage(), null));
                        } catch (IOException e) {
                            // doNothing
                        }*/
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                handleThrowableException(t, mutableLiveData);
            }
        });
    }


}
