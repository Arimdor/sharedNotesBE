package com.arimdor.sharedNotesBE.util;

import java.util.List;

public class ResponseListModel {
    private List<?> data;
    private String message;

    public ResponseListModel() {
    }

    public ResponseListModel(List<?> data, String message) {
        this.data = data;
        this.message = message;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
