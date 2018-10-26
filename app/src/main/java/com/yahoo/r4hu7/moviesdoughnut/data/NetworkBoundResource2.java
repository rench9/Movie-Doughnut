package com.yahoo.r4hu7.moviesdoughnut.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

import com.yahoo.r4hu7.moviesdoughnut.data.remote.ApiEmptyResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.ApiErrorResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.ApiResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.ApiSuccessResponse;
import com.yahoo.r4hu7.moviesdoughnut.util.AppExecutors;

public abstract class NetworkBoundResource2<ResultType, RequestType> {
    private AppExecutors appExecutors;
    private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource2(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> setValue(Resource.success(newData)));
            }
        });
    }

    private void setValue(Resource<ResultType> newValue) {
        if (this.result.getValue() != newValue)
            this.result.setValue(newValue);
    }

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        result.addSource(dbSource, newData ->
                setValue(Resource.loading(newData))
        );
        result.addSource(apiResponse, requestTypeApiResponse -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            if (requestTypeApiResponse instanceof ApiSuccessResponse) {
                appExecutors.diskIO().execute(() -> {
                    saveCallResult(processResponse((ApiSuccessResponse<RequestType>) requestTypeApiResponse));
                    appExecutors.mainThread().execute(() -> result.addSource(loadFromDb(), newData -> setValue(Resource.success(newData))));
                });

            } else if (requestTypeApiResponse instanceof ApiEmptyResponse) {
                appExecutors.mainThread().execute(() -> result.addSource(loadFromDb(), newData -> setValue(Resource.success(newData))));

            } else if (requestTypeApiResponse instanceof ApiErrorResponse) {
                onFetchFailed();
                result.addSource(dbSource, newData ->
                        setValue(Resource.error(((ApiErrorResponse) requestTypeApiResponse).getErrorMessage(), newData)));
            }
        });

    }

    protected void onFetchFailed() {
    }

    LiveData<Resource<ResultType>> asLiveDate() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiSuccessResponse<RequestType> response) {
        return response.getBody();
    }

    protected abstract void saveCallResult(RequestType item);

    protected abstract boolean shouldFetch(ResultType data);

    protected abstract LiveData<ResultType> loadFromDb();

    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
