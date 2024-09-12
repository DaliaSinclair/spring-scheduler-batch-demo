package org.example.scheduling;

import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class ScheduledLoggingTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledLoggingTask.class);

    private final RateLimiter rateLimiter;

    @Retryable(retryFor = Exception.class,
    maxAttemptsExpression = "${scheduling.retry.maxAttempts}",
    backoff = @Backoff(
            delayExpression = "${scheduling.retry.delayMs}",
            multiplierExpression = "${scheduling.retry.backoffMultiplier}"
    ))
    public void logMessage() {
        rateLimiter.acquire();
        log.info("LOG AT: " + LocalTime.now());
    }

    @Recover
    public void recover() {
        log.error("Failed to log message");
    }
}
