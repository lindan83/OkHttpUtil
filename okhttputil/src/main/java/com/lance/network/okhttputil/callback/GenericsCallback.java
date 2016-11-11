package com.lance.network.okhttputil.callback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

public abstract class GenericsCallback<T> extends Callback<T> {
    IGenericsSerializer mGenericsSerializer;

    public GenericsCallback(IGenericsSerializer serializer) {
        mGenericsSerializer = serializer;
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) string;
        }
        T bean = mGenericsSerializer.transform(string, entityClass);
        return bean;
    }
}