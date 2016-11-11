package com.lance.network.okhttputil.builder;

/**
 * 代表一个请求头参数
 */
public class HeaderEntry {
    private String headerName;
    private String headerValue;

    public HeaderEntry() {
    }

    public HeaderEntry(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }
}
