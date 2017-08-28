package com.lance.network.okhttputil.callback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class GenericsCallback<T> extends Callback<T> {
    private IGenericsSerializer genericsSerializer;

    public GenericsCallback(IGenericsSerializer serializer) {
        genericsSerializer = serializer;
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            String string = responseBody.string();
            Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            if (entityClass == String.class) {
                return (T) string;
            }
            T bean = genericsSerializer.transform(string, entityClass);
            return bean;
        }
        return null;
    }
}