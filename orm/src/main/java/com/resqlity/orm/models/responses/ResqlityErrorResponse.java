package com.resqlity.orm.models.responses;

import java.util.Dictionary;
import java.util.List;

public class ResqlityErrorResponse {
    private int status;
    private String type;
    private String message;

    public String getMessage() {
        return message;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
