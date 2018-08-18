package com.yahoo.r4hu7.moviesdoughnut.data.remote.response;

import android.arch.persistence.room.Ignore;

public class Response {
    @Ignore
    boolean success;
    @Ignore
    String status_message;
    @Ignore
    int status_code;

    public boolean isSuccess() {
        return success;
    }

    public String getStatus_message() {
        return status_message;
    }

    public int getStatus_code() {
        return status_code;
    }
}
