package com.jcms.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcms.domain.SysUserEntity;
import com.jcms.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:/redis.properties")
public class RedisConfig {
    private final static Logger log = LoggerFactory.getLogger(RedisConfig.class);
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private Integer port;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.maxIdle}")
    private Integer maxIdle;
    @Value("${redis.maxTotal}")
    private Integer maxTotal;
    @Value("${redis.maxWait}")
    private Integer maxWait;
    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${redis.timeout}")
    private Integer timeout;
    @Value("${redis.usePool}")
    private boolean usePool;

    @Bean
    public JedisPoolConfig poolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);//最大连接数：能够同时建立的“最大链接个数”
        config.setMaxIdle(maxIdle);//最大空闲数：空闲链接数大于maxIdle时，将进行回收
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        return config;
    }

    @Bean
    public RedisConnectionFactory redisCF(JedisPoolConfig poolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(poolConfig);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setTimeout(timeout);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisCF) {
        RedisTemplate<String,Object> template = new RedisTemplate<String,Object>();

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);


        template.setConnectionFactory(redisCF);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisCF){
//        StringRedisTemplate template = new StringRedisTemplate(redisCF);
//        template.setKeySerializer();
//        return ;
//    }
    @Bean
    public RedisUtils redisUtils(){
        return new RedisUtils();
    }
}
