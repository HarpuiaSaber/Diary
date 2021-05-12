package com.ttc.diary.models.response;

public class SystemResponse<T> {
    private int status;

    private String error;

    private T data;

    public SystemResponse() {
        //default constructor
    }

    public SystemResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public SystemResponse(int status, String error, T data) {
        this.status = status;
        this.error = error;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
