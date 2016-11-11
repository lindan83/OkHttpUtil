package com.lance.network.okhttputil.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lance.network.okhttputil.OkHttpUtils;
import com.lance.network.okhttputil.callback.JsonCallback;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView mTvBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvBook = (TextView) findViewById(R.id.tv_book);
    }

    public void sendRequest(View v) {
        OkHttpUtils.get().url("http://192.168.1.58:8080/TestWeb/object")
                .build().execute(new JsonCallback<Book>(Book.class) {
            @Override
            public void onError(Call call, Response response, Exception exception, int id) {
                Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Book response, int id) {
                if (response != null) {
                    mTvBook.setText("");

                    mTvBook.append("图书编号：");
                    mTvBook.append(String.valueOf(response.getBookId()));
                    mTvBook.append("\r\n");

                    mTvBook.append("图书名牌：");
                    mTvBook.append(response.getBookName());
                    mTvBook.append("\r\n");

                    mTvBook.append("作者：");
                    mTvBook.append(response.getAuthor());
                    mTvBook.append("\r\n");
                } else {
                    mTvBook.setText("没有数据");
                }
            }
        });
    }
}