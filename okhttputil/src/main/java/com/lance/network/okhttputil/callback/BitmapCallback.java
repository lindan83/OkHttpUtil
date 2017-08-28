package com.lance.network.okhttputil.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class BitmapCallback extends Callback<Bitmap> {
    @Override
    public Bitmap parseNetworkResponse(Response response, int id) throws Exception {
        if (response != null) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return BitmapFactory.decodeStream(responseBody.byteStream());
            }
        }
        return null;
    }
}
