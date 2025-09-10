package com.example.quoteapi.service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class RateLimitService {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final long capacity;
    private final Duration refillDuration;

    public RateLimitService(@Value("${rate.limit.capacity}") long capacity,
                           @Value("${rate.limit.refill.seconds}") long refillSeconds) {
        this.capacity = capacity;
        this.refillDuration = Duration.ofSeconds(refillSeconds);
    }

    public Bucket getBucket(String ipAddress) {
        return buckets.computeIfAbsent(ipAddress, k -> {
            Refill refill = Refill.greedy(capacity, refillDuration);
            Bandwidth limit = Bandwidth.classic(capacity, refill);
            return Bucket.builder().addLimit(limit).build();
        });
    }
}