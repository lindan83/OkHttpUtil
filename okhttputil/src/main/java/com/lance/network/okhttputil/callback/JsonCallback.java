package com.lance.network.okhttputil.callback;

import com.lance.network.okhttputil.utils.JSONUtil;

import java.io.IOException;

import okhttp3.Response;

public abstract class JsonCallback<T> extends Callback<T> {
    private Class<T> clazz;

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String json = response.body().string();
        return JSONUtil.getObjectFromJson(json, clazz);
    }
}