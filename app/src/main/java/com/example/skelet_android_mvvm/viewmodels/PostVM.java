package com.example.skelet_android_mvvm.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.skelet_android_mvvm.models.Post;
import com.example.skelet_android_mvvm.repositories.PostRepo;
import com.example.skelet_android_mvvm.utils.Resource;

import java.util.List;

public class PostVM extends AndroidViewModel {

    private MutableLiveData<Resource<List<Post>>> postsList;

    public PostVM(@NonNull Application application) {
        super(application);
        postsList = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<List<Post>>> getPostsListLiveData(){
        return postsList;
    }
    public void getPost(){
        new PostRepo().getPosts(postsList);
    }



}
