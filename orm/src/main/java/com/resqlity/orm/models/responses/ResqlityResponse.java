package com.resqlity.orm.models.responses;

public class ResqlityResponse<T> {
    private final String status;
    private final String message;
    private final T data;

    public ResqlityResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
