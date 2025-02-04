package com.sycodes.URLshortener.controller;

import com.sycodes.URLshortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestParam String originalUrl) {
        String shortUrl = urlService.shortenUrl(originalUrl);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public  ResponseEntity<String> getOriginalUrl(@PathVariable String shortCode){
        String shortUrl = "https://short.ly/" + shortCode;
        String originalUrl = urlService.getOriginalUrl(shortUrl);

        return originalUrl != null ? ResponseEntity.ok(originalUrl) : ResponseEntity.notFound().build();
    }
}
