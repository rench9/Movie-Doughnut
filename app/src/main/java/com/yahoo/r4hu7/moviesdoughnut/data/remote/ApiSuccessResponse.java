package com.yahoo.r4hu7.moviesdoughnut.data.remote;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieReviewsResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MoviesSource;

public class ApiSuccessResponse<T> extends ApiResponse<T> {
    private T body;

    public ApiSuccessResponse(T body) {
        this.body = body;
    }

    public T getBody() {
        return body;
    }


    public int getNextPage() {
        if (body instanceof MoviesSource)
            return ((MoviesSource) body).getPage() + 1;
        else if (body instanceof MovieReviewsResponse)
            return ((MovieReviewsResponse) body).getPage() + 1;
        return 0;
    }
}
