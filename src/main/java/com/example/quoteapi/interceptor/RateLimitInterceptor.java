package com.example.quoteapi.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.quoteapi.service.RateLimitService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);
    private final RateLimitService rateLimitService;

    public RateLimitInterceptor(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
        Bucket bucket = rateLimitService.getBucket(ipAddress);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            logger.info("Request from IP: {}, Path: {}, Status: {}", ipAddress, request.getRequestURI(), HttpStatus.OK.value());
            return true;
        } else {
            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Rate limit exceeded. Try again in " + waitForRefill + " seconds.\"}");
            logger.info("Request from IP: {}, Path: {}, Status: {}", ipAddress, request.getRequestURI(), HttpStatus.TOO_MANY_REQUESTS.value());
            return false;
        }
    }
}