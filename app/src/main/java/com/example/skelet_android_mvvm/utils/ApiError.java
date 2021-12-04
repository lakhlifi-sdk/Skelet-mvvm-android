package com.example.skelet_android_mvvm.utils;

import com.google.gson.annotations.SerializedName;

public class ApiError {

	@SerializedName("error")
	private int error;

	@SerializedName("message")
	private String message;

	public int getError(){
		return error;
	}

	public String getMessage(){
		return message;
	}
}