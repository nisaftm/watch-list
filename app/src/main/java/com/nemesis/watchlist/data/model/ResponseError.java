package com.nemesis.watchlist.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ResponseError implements Serializable {

	@SerializedName("status_code")
	private int statusCode;

	@SerializedName("status_message")
	private String statusMessage;

	@SerializedName("success")
	private boolean success;

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}

	public void setStatusMessage(String statusMessage){
		this.statusMessage = statusMessage;
	}

	public String getStatusMessage(){
		return statusMessage;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	@Override
 	public String toString(){
		return 
			"ResponseError{" + 
			"status_code = '" + statusCode + '\'' + 
			",status_message = '" + statusMessage + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}