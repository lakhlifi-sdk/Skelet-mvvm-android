package com.example.skelet_android_mvvm.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.skelet_android_mvvm.datamanager.DataManager;
import com.example.skelet_android_mvvm.models.Post;
import com.example.skelet_android_mvvm.utils.Resource;

import java.util.List;

public class PostRepo {
    public void getPosts(MutableLiveData<Resource<List<Post>>> mutableLiveData){
        new DataManager().getPosts(mutableLiveData);
    }
}
