package com.lance.network.okhttputil.builder;

/**
 * 表示一个请求参数
 */
public class RequestEntry {
    private String key;
    private String value;

    public RequestEntry() {
    }

    public RequestEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
