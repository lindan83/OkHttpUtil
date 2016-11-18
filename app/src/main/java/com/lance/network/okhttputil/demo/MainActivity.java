package com.lance.network.okhttputil.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lance.network.okhttputil.OkHttpUtils;
import com.lance.network.okhttputil.callback.JsonCallback;

import java.util.List;

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
        final int id = v.getId();
        switch (id) {
            case R.id.btn_get_book:
                getBook(1);
                break;
            case R.id.btn_get_books:
                getBook(2);
                break;
        }
    }

    private void getBook(int type) {
        if (type == 1) {
            OkHttpUtils.get().url("http://192.168.1.58:8080/TestWeb/object")
                    .addParams("type", "1")
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
        } else if (type == 2) {
            OkHttpUtils.get().url("http://192.168.1.58:8080/TestWeb/object")
                    .addParams("type", "2")
                    .build().execute(new JsonCallback<List<Book>>(new TypeToken<List<Book>>() {
            }.getType()) {
                @Override
                public void onError(Call call, Response response, Exception exception, int id) {
                    Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(List<Book> response, int id) {
                    if (response != null) {
                        mTvBook.setText("");
                        StringBuilder stringBuilder = new StringBuilder(60);
                        for (Book book : response) {
                            stringBuilder
                                    .append("图书编号：")
                                    .append(String.valueOf(book.getBookId())).append("\r\n")
                                    .append("图书名牌：")
                                    .append(book.getBookName()).append("\r\n")
                                    .append("作者：")
                                    .append(book.getAuthor()).append("\r\n");
                        }
                        mTvBook.setText(stringBuilder.toString());
                    } else {
                        mTvBook.setText("没有数据");
                    }
                }
            });
        }
    }
}