package com.lance.network.okhttputil.builder;

import com.lance.network.okhttputil.OkHttpUtils;
import com.lance.network.okhttputil.request.OtherRequest;
import com.lance.network.okhttputil.request.RequestCall;

public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
