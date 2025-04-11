package com.courseproject.pointofsaleservice.configuration;

import com.courseproject.pointofsaleservice.models.Customer;
import com.courseproject.pointofsaleservice.models.Employee;
import com.courseproject.pointofsaleservice.models.Register;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    RedisTemplate<Long, Employee> employeeRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Long, Employee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    RedisTemplate<Long, Customer> customerRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Long, Customer> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    RedisTemplate<Long, Register> registerRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Long, Register> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
