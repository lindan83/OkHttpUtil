package com.lance.network.okhttputil.request;

import com.lance.network.okhttputil.builder.HeaderEntry;
import com.lance.network.okhttputil.builder.RequestEntry;
import com.lance.network.okhttputil.callback.Callback;

import java.util.List;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

public abstract class OkHttpRequest {
    protected String url;
    protected Object tag;
    protected List<RequestEntry> params;
    protected List<HeaderEntry> headers;
    protected int id;

    protected Request.Builder builder = new Request.Builder();

    protected OkHttpRequest(String url, Object tag, List<RequestEntry> params, List<HeaderEntry> headers, int id) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.id = id;

        if (url == null) {
            throw new IllegalArgumentException("url can not be null.");
        }

        initBuilder();
    }

    private void initBuilder() {
        builder.url(url).tag(tag);
        appendHeaders();
    }

    protected abstract RequestBody buildRequestBody();

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        return requestBody;
    }

    protected abstract Request buildRequest(RequestBody requestBody);

    public RequestCall build() {
        return new RequestCall(this);
    }


    public Request generateRequest(Callback callback) {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        return buildRequest(wrappedRequestBody);
    }


    protected void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (HeaderEntry headerEntry : headers) {
            headerBuilder.add(headerEntry.getHeaderName(), headerEntry.getHeaderValue());
        }
        builder.headers(headerBuilder.build());
    }

    public int getId() {
        return id;
    }
}