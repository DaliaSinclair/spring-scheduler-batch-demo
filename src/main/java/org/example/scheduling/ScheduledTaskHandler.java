package org.example.scheduling;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableSchedulerLock(defaultLockAtMostFor = "10m") // Default in case this isn't configured
public class ScheduledTaskHandler {

    private final ScheduledLoggingTask scheduledLoggingTask;

    @Scheduled(cron = "${scheduling.tasks.schedule}", zone = "${scheduling.timezone}")
    @SchedulerLock(name = "runLoggingTask",
        lockAtLeastFor = "${scheduling.shedlock.lockAtLeastFor}",
        lockAtMostFor = "${scheduling.shedlock.lockAtMostFor}")
    public void runLoggingTask() {
        int numLogs = 10;
        for (int i = 0; i < numLogs; i++) {
            scheduledLoggingTask.logMessage();
        }
    }
}
