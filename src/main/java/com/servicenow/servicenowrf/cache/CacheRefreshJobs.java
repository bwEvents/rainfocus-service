package com.servicenow.servicenowrf.cache;

import com.servicenow.servicenowrf.rainfocus.configuration.Properties;
import com.servicenow.servicenowrf.rainfocus.service.RainFocusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheRefreshJobs {
    private final Properties properties;
    private final RainFocusService rainFocusService;

    public CacheRefreshJobs(Properties properties, RainFocusService rainFocusService) {
        this.properties = properties;
        this.rainFocusService = rainFocusService;
    }

    @Scheduled(fixedDelay = 900000L) // 15 mins
    public void refreshSessionsForAllEvents() {
        properties.getEvents().forEach(event -> {
            rainFocusService.getAllAcceptedSessions(event);
            rainFocusService.fetchAllSponsors(event, null);
            rainFocusService.fetchSpeakers(event);
        });
    }
}
