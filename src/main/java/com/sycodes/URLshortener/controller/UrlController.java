package com.sycodes.URLshortener.controller;

import com.sycodes.URLshortener.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("shorten")
    public ResponseEntity<String> shortenUrl(@RequestParam String originalUrl,
                                             @RequestParam(required = false) String customShortCode) {
        try {
            String shortUrl = urlService.shortenUrl(originalUrl, customShortCode);
            return ResponseEntity.ok(shortUrl);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        String shortUrl = "https://localhost:8080/" + shortCode;
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        if (originalUrl != null) {
            response.sendRedirect(originalUrl);
            return ResponseEntity.status(HttpStatus.FOUND).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}