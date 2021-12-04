package com.example.skelet_android_mvvm.utils;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class Connectivity implements LifecycleObserver {
    private ConnectivityManager connectivityManager;
    public Connectivity(@NonNull Context context, @Nullable LifecycleOwner lifecycleOwner) {
        try {
            Context context1 = context.getApplicationContext();
            connectivityManager = (ConnectivityManager) context1.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (lifecycleOwner != null) {
                lifecycleOwner.getLifecycle().addObserver(this);
            }
        }catch (IllegalArgumentException iae){
            Log.d("TAG", "Context cannot be null!: "+iae.getMessage());
        }
    }

    @Nullable
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public NetworkInfo getActiveNetworkInfo() {
        NetworkInfo networkInfo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            networkInfo = connectivityManager.getNetworkInfo(connectivityManager.getActiveNetwork());
        } else {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo;
    }

    public boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }



}
