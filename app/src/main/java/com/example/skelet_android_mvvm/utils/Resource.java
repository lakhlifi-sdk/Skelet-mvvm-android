package com.example.skelet_android_mvvm.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T>  {

    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;

    private Resource(@NonNull Status status, @Nullable T data,
                     @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> invalidToken(@Nullable T data) {
        return new Resource<>(Status.INVALID_TOKEN, data, null);
    }

    public enum Status {SUCCESS, ERROR, INVALID_TOKEN}
}