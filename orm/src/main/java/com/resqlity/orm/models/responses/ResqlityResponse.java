package com.resqlity.orm.models.responses;

public class ResqlityResponse<T> extends ResqlitySimpleResponse {
    private T data;

    public ResqlityResponse(String message, boolean success) {
        super(message, success);
    }




    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}