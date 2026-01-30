package com.urlshortener.service;

import com.urlshortener.model.UrlMapping;
import com.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

@Service
public class UrlShortenerService {

    private final UrlRepository repo;
    private static final String BASE62 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public UrlShortenerService(UrlRepository repo) {
        this.repo = repo;
    }

    public String shortenUrl(String longUrl) {
        return repo.findByLongUrl(longUrl)
                .map(UrlMapping::getShortCode)
                .orElseGet(() -> {
                    UrlMapping saved = new UrlMapping();
                    saved.setLongUrl(longUrl);
                    saved = repo.save(saved);

                    String code = encode(saved.getId());
                    saved.setShortCode(code);
                    repo.save(saved);

                    return code;
                });
    }

    @Cacheable(value = "shortUrls", key = "#code")
    public String expandUrl(String code) {
        return repo.findByShortCode(code)
                .map(UrlMapping::getLongUrl)
                .orElse(null);
    }

    private String encode(Long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62.charAt((int) (num % 62)));
            num /= 62;
        }
        return sb.reverse().toString();
    }
}
