package com.lance.network.okhttputil.request;

import com.lance.network.okhttputil.builder.HeaderEntry;
import com.lance.network.okhttputil.builder.RequestEntry;

import java.util.List;

import okhttp3.Request;
import okhttp3.RequestBody;

public class GetRequest extends OkHttpRequest {
    public GetRequest(String url, Object tag, List<RequestEntry> params, List<HeaderEntry> headers, int id) {
        super(url, tag, params, headers, id);
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.get().build();
    }
}