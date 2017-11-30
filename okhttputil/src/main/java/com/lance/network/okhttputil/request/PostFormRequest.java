package com.lance.network.okhttputil.request;

import com.lance.network.okhttputil.OkHttpUtils;
import com.lance.network.okhttputil.builder.HeaderEntry;
import com.lance.network.okhttputil.builder.PostFormBuilder;
import com.lance.network.okhttputil.builder.RequestEntry;
import com.lance.network.okhttputil.callback.Callback;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostFormRequest extends OkHttpRequest {
    private List<PostFormBuilder.FileInput> files;

    public PostFormRequest(String url, Object tag, List<RequestEntry> params, List<HeaderEntry> headers, List<PostFormBuilder.FileInput> files, int id) {
        super(url, tag, params, headers, id);
        this.files = files;
    }

    @Override
    protected RequestBody buildRequestBody() {
        final List<PostFormBuilder.FileInput> files = this.files;
        if (files == null || files.isEmpty()) {
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder);
            return builder.build();
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            addParams(builder);

            for (int i = 0, count = files.size(); i < count; i++) {
                PostFormBuilder.FileInput fileInput = files.get(i);
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.filename)), fileInput.file);
                builder.addFormDataPart(fileInput.key, fileInput.filename, fileBody);
            }
            return builder.build();
        }
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        if (callback == null) {
            return requestBody;
        }
        return new CountingRequestBody(requestBody, (bytesWritten, contentLength) -> OkHttpUtils.getInstance().getDelivery().execute(() -> callback.inProgress(bytesWritten * 1.0f / contentLength, contentLength, id)));
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void addParams(MultipartBody.Builder builder) {
        final List<RequestEntry> params = this.params;
        if (params != null && !params.isEmpty()) {
            for (RequestEntry requestEntry : params) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + requestEntry.getKey() + "\""),
                        RequestBody.create(null, requestEntry.getValue()));
            }
        }
    }

    private void addParams(FormBody.Builder builder) {
        final List<RequestEntry> params = this.params;
        if (params != null) {
            for (RequestEntry requestEntry : params) {
                builder.add(requestEntry.getKey(), requestEntry.getValue());
            }
        }
    }
}