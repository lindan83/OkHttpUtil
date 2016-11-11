package com.lance.network.okhttputil.builder;

import com.lance.network.okhttputil.request.RequestCall;

import java.util.ArrayList;
import java.util.List;

public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {
    protected String url;
    protected Object tag;
    protected List<HeaderEntry> headers;
    protected List<RequestEntry> params;
    protected int id;

    public T id(int id) {
        this.id = id;
        return (T) this;
    }

    public T url(String url) {
        this.url = url;
        return (T) this;
    }


    public T tag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    public T headers(List<HeaderEntry> headers) {
        this.headers = headers;
        return (T) this;
    }

    public T addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new ArrayList<>();
        }
        headers.add(new HeaderEntry(key, val));
        return (T) this;
    }

    public abstract RequestCall build();
}