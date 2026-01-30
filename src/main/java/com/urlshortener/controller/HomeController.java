package com.urlshortener.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
        🚀 URL Shortener API is running!

        Endpoints:
        • POST /api/shorten
        • GET  /{shortCode}
        • GET  /api/ping
        """;
    }
}
