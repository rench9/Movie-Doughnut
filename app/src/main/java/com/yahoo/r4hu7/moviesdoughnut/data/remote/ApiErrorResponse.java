package com.yahoo.r4hu7.moviesdoughnut.data.remote;

public class ApiErrorResponse<T> extends ApiResponse<T> {
    String errorMessage;

    public ApiErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
