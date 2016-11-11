package com.lance.network.okhttputil.request;

import com.lance.network.okhttputil.builder.HeaderEntry;
import com.lance.network.okhttputil.builder.RequestEntry;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostStringRequest extends OkHttpRequest {
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    private String content;
    private MediaType mediaType;


    public PostStringRequest(String url, Object tag, List<RequestEntry> params, List<HeaderEntry> headers, String content, MediaType mediaType, int id) {
        super(url, tag, params, headers, id);
        this.content = content;
        this.mediaType = mediaType;

        if (this.content == null) {
            throw new IllegalArgumentException("the content can not be null !");
        }
        if (this.mediaType == null) {
            this.mediaType = MEDIA_TYPE_PLAIN;
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mediaType, content);
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }
}