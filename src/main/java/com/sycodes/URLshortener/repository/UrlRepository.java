package com.sycodes.URLshortener.repository;

import com.sycodes.URLshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortUrl(String shortUrl);
    Optional<Url> findByOriginalUrl(String originalUrl);
}
