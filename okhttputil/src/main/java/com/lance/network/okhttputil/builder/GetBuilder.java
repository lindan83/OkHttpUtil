package com.lance.network.okhttputil.builder;

import android.net.Uri;

import com.lance.network.okhttputil.request.GetRequest;
import com.lance.network.okhttputil.request.RequestCall;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> implements HasParamsable {
    @Override
    public RequestCall build() {
        if (params != null) {
            url = appendParams(url, params);
        }

        return new GetRequest(url, tag, params, headers, id).build();
    }

    protected String appendParams(String url, List<RequestEntry> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Iterator<RequestEntry> iterator = params.iterator();
        while (iterator.hasNext()) {
            RequestEntry requestEntry = iterator.next();
            String key = requestEntry.getKey();
            String value = requestEntry.getValue();
            builder.appendQueryParameter(key, value);
        }
        return builder.build().toString();
    }


    @Override
    public GetBuilder params(List<RequestEntry> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new ArrayList<>();
        }
        params.add(new RequestEntry(key, val));
        return this;
    }
}