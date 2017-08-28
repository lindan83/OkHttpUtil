package com.lance.network.okhttputil.callback;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class StringCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            return responseBody.string();
        }
        return null;
    }
}