package com.lance.network.okhttputil.builder;

import java.util.List;

public interface HasParamsable {
    OkHttpRequestBuilder params(List<RequestEntry> params);

    OkHttpRequestBuilder addParams(String key, String val);
}
