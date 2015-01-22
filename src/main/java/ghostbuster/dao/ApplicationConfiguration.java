package ghostbuster.dao;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
@EnableAutoConfiguration
public class ApplicationConfiguration {


    @Bean
    public CacheManager cacheManager() {
        Cache cache = new ConcurrentMapCache("byUsername");
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(cache));
        return manager;
    }
}