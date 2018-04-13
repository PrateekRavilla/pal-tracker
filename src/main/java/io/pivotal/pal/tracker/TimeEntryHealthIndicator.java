package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {
   private static final int size=5;
   private TimeEntryRepository  timeentryrepo;

    public TimeEntryHealthIndicator(TimeEntryRepository timeentryrepo) {
        this.timeentryrepo = timeentryrepo;
    }

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();

        if(timeentryrepo.list().size() < size) {
            builder.up();
        } else {
            builder.down();
        }

        return builder.build();
    }
}

