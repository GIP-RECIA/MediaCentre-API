package fr.recia.mediacentre.api.configuration.cache;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class CacheEventLogger implements CacheEventListener<Object,Object> {
    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        log.info("CacheEvent = {} , Key = {} , OldValue = {} , NewValue = {}", cacheEvent.getType(),
                cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}
