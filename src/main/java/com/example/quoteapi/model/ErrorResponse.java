package com.example.quoteapi.model;

public class ErrorResponse {
	private String error;

    public ErrorResponse() {
        // Default constructor
    }

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
