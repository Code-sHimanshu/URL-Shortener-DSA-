package com.urlshortener.service;

import java.util.HashMap;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    private static final String BASE62 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private HashMap<String, String> shortToLong = new HashMap<>();
    private HashMap<String, String> longToShort = new HashMap<>();
    private int id = 1;

    public String shortenUrl(String longUrl) {

        if (!longUrl.startsWith("http")) {
            return "Invalid URL";
        }

        if (longToShort.containsKey(longUrl)) {
            return longToShort.get(longUrl);
        }

        String code = encode(id++);
        shortToLong.put(code, longUrl);
        longToShort.put(longUrl, code);

        return code;
    }

    public String expandUrl(String code) {
        return shortToLong.getOrDefault(code, "URL not found");
    }

    private String encode(int num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62.charAt(num % 62));
            num /= 62;
        }
        return sb.reverse().toString();
    }
}
