package com.example.skelet_android_mvvm.utils;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleLiveData<T> extends MutableLiveData<T> {
    private final AtomicBoolean isPending = new AtomicBoolean(false);

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        if(hasActiveObservers()){
            final String TAG = "SingleLiveEvent";
            Log.w(TAG, "Data has multiple observer but only one will be used");
        }
        super.observe(owner, t -> {
            if(isPending.compareAndSet(true,false)){
                observer.onChanged(t);
            }
        });
    }

    @MainThread
    @Override
    public void setValue(T value) {
        isPending.set(true);
        super.setValue(value);
    }

    @MainThread
    public void call(){
        setValue(null);
    }

    @Override
    public void removeObservers(@NonNull LifecycleOwner owner) {
        super.removeObservers(owner);
    }
}