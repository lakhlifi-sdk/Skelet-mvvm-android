package com.example.skelet_android_mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.skelet_android_mvvm.models.Post;
import com.example.skelet_android_mvvm.utils.Resource;
import com.example.skelet_android_mvvm.viewmodels.PostVM;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PostVM viewModel;
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewModel =  new ViewModelProvider(requireActivity()).get(PostVM.class);
        //viewModel.getPostsListLiveData().observe(getViewLifecycleOwner(), this::handlePostData);

        viewModel =  new ViewModelProvider(this).get(PostVM.class);
        viewModel.getPostsListLiveData().observe(this, this::handlePostData);
        viewModel.getPost();

    }

    private void handlePostData(Resource<List<Post>> listResource) {
        switch (listResource.status){
            case SUCCESS:
                //TODO use data in your activity views

                for (Post post : listResource.data){
                    Log.d(TAG, "post : "+post);
                }
                break;


            case ERROR:
                Log.d(TAG, "ERROR->->>: ");

                //TODO show error message

                break;

            case INVALID_TOKEN:
                Log.d(TAG, "INVALID_TOKEN->->->->: ");

                //TODO go to login activity

                break;

        }
    }

}