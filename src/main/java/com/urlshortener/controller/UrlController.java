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

    @PostMapping("/shorten")
    public String shorten(@RequestBody UrlRequest request) {
        return service.shortenUrl(request.getLongUrl());
    }

    // TEMP TEST ENDPOINT (IMPORTANT)
    @GetMapping("/ping")
    public String ping() {
        return "API is alive";
    }

    @GetMapping("/{code}")
    public void redirect(
        @PathVariable String code,
        HttpServletResponse response) throws IOException {

    String url = service.expandUrl(code);

    if (url == null || url.isBlank()) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
    }

    response.sendRedirect(url);
    }


}
