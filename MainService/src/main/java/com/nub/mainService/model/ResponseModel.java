package com.nub.mainService.model;


import java.io.Serializable;

public class ResponseModel<E> implements Serializable {
    private long responseID;
    private E data;
    private String message;

    public ResponseModel() {
    }

    public ResponseModel(long responseID, E data, String message) {
        this.responseID = responseID;
        this.data = data;
        this.message = message;
    }

    public long getResponseID() {
        return responseID;
    }

    public void setResponseID(long responseID) {
        this.responseID = responseID;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

