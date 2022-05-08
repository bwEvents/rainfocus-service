package com.servicenow.servicenowrf.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CacheController {

    @DeleteMapping("/events/{eventId}/sessions/caches/evict")
    @CacheEvict(value = "acceptedSessionsForEvent", key = "#eventId")
    public void evictSessionCache(@PathVariable String eventId) {
        log.info("Removing sessions cache for eventId {}", eventId);
    }

    @DeleteMapping("/events/{eventId}/speakers/caches/evict")
    @CacheEvict(value = "allSpeakersForEvent", key = "#eventId")
    public void evictSpeakerCache(@PathVariable String eventId) {
        log.info("Removing speakers cache for eventId {}", eventId);
    }

    @DeleteMapping("/events/{eventId}/exhibitors/caches/evict")
    @CacheEvict(value = "allSponsorsForEvent", key = "#eventId")
    public void evictExhibitorsCache(@PathVariable String eventId) {
        log.info("Removing exhibitors cache for eventId {}", eventId);
    }

    @DeleteMapping("/events/{eventId}/sessionPosters/caches/evict")
    @CacheEvict(value = "sessionPosters", key = "#eventId")
    public void evictSessionPostersCache(@PathVariable String eventId) {
        log.info("Removing session posters cache for eventId {}", eventId);
    }
}
