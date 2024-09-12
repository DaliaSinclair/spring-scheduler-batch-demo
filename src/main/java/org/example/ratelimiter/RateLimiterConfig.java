package org.example.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {

    @Bean
    public RateLimiter loggingRateLimiter(
            final @Value("${scheduling.rateLimiter.maxRatePerSecond}") int maxRatePerSecond) {
        return RateLimiter.create(maxRatePerSecond);
    }
}
