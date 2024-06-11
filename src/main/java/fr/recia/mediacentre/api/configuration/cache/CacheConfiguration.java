package fr.recia.mediacentre.api.configuration.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class CacheConfiguration {

//    @Bean
//    public CacheManager cacheManager() {
//        CachingProvider provider = Caching.getCachingProvider();
//        CacheManager cacheManager = null;
//        try {
//            cacheManager = provider.getCacheManager(
//                    getClass().getResource("/ehcache.xml").toURI(),
//                    getClass().getClassLoader());
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        return cacheManager;
//    }
}
