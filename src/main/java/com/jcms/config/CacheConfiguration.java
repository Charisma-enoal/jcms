package com.jcms.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CacheConfiguration {
//    @Bean
//    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
//        EhCacheManagerFactoryBean factoryBean=new EhCacheManagerFactoryBean();
//        factoryBean.setConfigLocation(new ClassPathResource("classpath:cache/ehcache.xml"));
//        factoryBean.setShared(true);//也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
//        return factoryBean;
//    }
//    @Bean
//    public EhCacheCacheManager ehCacheCacheManager(){
//        System.out.println("CacheConfiguration.ehcacheManager()");
//        EhCacheCacheManager cacheManager = new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
//        return cacheManager;
//
//    }
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate){
        return new RedisCacheManager(redisTemplate);
    }
}
