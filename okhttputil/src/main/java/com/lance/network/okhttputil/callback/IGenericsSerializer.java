package com.lance.network.okhttputil.callback;

public interface IGenericsSerializer {
    <T> T transform(String response, Class<T> classOfT);
}