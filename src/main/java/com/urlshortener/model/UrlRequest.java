package com.urlshortener.model;

public class UrlRequest {

    private String longUrl;

    public UrlRequest() {
        // default constructor REQUIRED
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
