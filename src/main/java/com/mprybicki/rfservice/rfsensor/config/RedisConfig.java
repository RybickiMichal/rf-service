package com.mprybicki.rfservice.rfsensor.config;

import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.jedis.*;
import org.springframework.data.redis.core.*;

@Configuration
public class RedisConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
