package com.yahoo.r4hu7.moviesdoughnut.data.remote;


import java.io.IOException;

import retrofit2.Response;

public class ApiResponse<T> {
    static <T> ApiErrorResponse<T> create(Throwable error) {
        return new ApiErrorResponse(error.getMessage() != null ? error.getMessage() : "unknown error");
    }

    static <T> ApiResponse<T> create(Response<T> response) {
        if (response.isSuccessful()) {
            if (response.body() == null || response.code() == 204)
                return new ApiEmptyResponse<>();
            else
                return new ApiSuccessResponse(
                        response.body());
        } else {
            String errorMessage;
            try {
                String message = response.errorBody() != null ? response.errorBody().string() : null;
                if (message != null && !message.isEmpty())
                    errorMessage = message;
                else errorMessage = response.message();

            } catch (IOException e) {
                e.printStackTrace();
                errorMessage = null;
            }
            return new ApiErrorResponse(errorMessage != null ? errorMessage : "unknown error");

        }
    }
}


