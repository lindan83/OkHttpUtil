package com.lance.network.okhttputil;

import android.support.annotation.NonNull;

import com.lance.network.okhttputil.builder.GetBuilder;
import com.lance.network.okhttputil.builder.HeadBuilder;
import com.lance.network.okhttputil.builder.OtherRequestBuilder;
import com.lance.network.okhttputil.builder.PostFileBuilder;
import com.lance.network.okhttputil.builder.PostFormBuilder;
import com.lance.network.okhttputil.builder.PostStringBuilder;
import com.lance.network.okhttputil.callback.Callback;
import com.lance.network.okhttputil.request.RequestCall;
import com.lance.network.okhttputil.utils.Platform;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OkHttpUtils instance;
    private OkHttpClient okHttpClient;
    private Platform platform;

    private OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            this.okHttpClient = new OkHttpClient();
        } else {
            this.okHttpClient = okHttpClient;
        }
        platform = Platform.get();
    }

    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return instance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }

    public Executor getDelivery() {
        return platform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null) {
            callback = Callback.CALLBACK_DEFAULT;
        }
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                sendFailResultCallback(call, null, e, finalCallback, id);
            }

            @Override
            public void onResponse(@NonNull final Call call, @NonNull final Response response) {
                ResponseBody responseBody = response.body();
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, response, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateResponse(response, id)) {
                        sendFailResultCallback(call, response, new IOException("request failed , response's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, response, e, finalCallback, id);
                } finally {
                    if (responseBody != null) {
                        responseBody.close();
                    }
                }
            }
        });
    }

    public void sendFailResultCallback(final Call call, final Response response, final Exception e, final Callback callback, final int id) {
        if (callback == null) {
            return;
        }
        platform.execute(() -> {
            callback.onError(call, response, e, id);
            callback.onAfter(id);
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) {
            return;
        }
        platform.execute(() -> {
            callback.onResponse(object, id);
            callback.onAfter(id);

        });
    }

    public void cancelTag(Object tag) {
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}