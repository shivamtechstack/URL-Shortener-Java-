package com.sycodes.URLshortener.service;

import com.sycodes.URLshortener.model.Url;
import com.sycodes.URLshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private static final String BASE_URL = "https://short.ly/";

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl, String customShortCode) {
        Optional<Url> existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl.isPresent()) {
            return  existingUrl.get().getShortUrl();
        }

        String shortCode;
        if(customShortCode!=null && !customShortCode.isEmpty()) {
            if (urlRepository.findByShortUrl(customShortCode).isPresent()) {
                throw new RuntimeException("custom short URl is already taken.");
            }
            shortCode = customShortCode;

        }else {
            shortCode = generatedShortCode();
        }

        String shortUrl = BASE_URL + shortCode;
        Url url = new Url(originalUrl, shortUrl);
        urlRepository.save(url);

        return shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        return  urlRepository.findByShortUrl(shortUrl)
                .map(Url::getOriginalUrl)
                .orElse(null);
    }

    private String generatedShortCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder shortCode = new StringBuilder();
        Random random = new Random();

        for (int i=0; i<6;i++){
            shortCode.append(chars.charAt(random.nextInt(chars.length())));
        }
        return shortCode.toString();
    }

}
