package ru.agcon.insurance_company.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Repository;
import ru.agcon.insurance_company.models.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class CartRepository {
    private final RedisTemplate<String, Cart> redisTemplate;

    @Autowired
    public CartRepository(RedisTemplate<String, Cart> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        this.redisTemplate.afterPropertiesSet();
    }

    public void save(String key, Cart value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Optional<Cart> findByLogin(String key) {
        Cart value = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(value);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public Set<String> getAllKeys(){
        return redisTemplate.keys("*");
    }

    public List<Cart> findAll() {
        Set<String> keys = redisTemplate.keys("*");
        return new ArrayList<>(redisTemplate.opsForValue().multiGet(keys));
    }
}
