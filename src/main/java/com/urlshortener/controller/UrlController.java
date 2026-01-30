package com.urlshortener.controller;

import com.urlshortener.model.UrlRequest;
import com.urlshortener.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlShortenerService service;

    public UrlController(UrlShortenerService service) {
        this.service = service;
    }

    // POST → shorten URL
    @PostMapping("/shorten")
    public String shorten(@RequestBody UrlRequest request) {
        return service.shortenUrl(request.getLongUrl());
    }

    // Health check
    @GetMapping("/ping")
    public String ping() {
        return "API is alive";
    }

    // REDIRECT (THIS IS THE ONLY ONE YOU NEED)
    @GetMapping("/{code}")
    public void redirect(
            @PathVariable String code,
            HttpServletResponse response) throws IOException {

        String longUrl = service.expandUrl(code);

        if (longUrl == null || longUrl.isBlank()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short URL not found");
            return;
        }

        response.sendRedirect(longUrl); // 302 redirect
    }
}
