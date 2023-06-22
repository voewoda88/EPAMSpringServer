package com.example.myspring.models;

import org.springframework.http.HttpStatusCode;

public class ExceptionModel {
    private String message;

    private HttpStatusCode error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatusCode getError() {
        return error;
    }

    public void setError(HttpStatusCode error) {
        this.error = error;
    }
}
