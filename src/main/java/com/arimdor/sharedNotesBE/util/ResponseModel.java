package com.arimdor.sharedNotesBE.util;


public class ResponseModel {
    private Object data;
    private String message;

    public ResponseModel(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    public ResponseModel() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

