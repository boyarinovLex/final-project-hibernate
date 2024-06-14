package com.javarush.boyarinov.config;

import com.javarush.boyarinov.exception.AppException;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public class RedisConnection {

    public RedisClient prepareRedisClient() {
        RedisClient redisClient = RedisClient.create(RedisURI.create("localhost", RedisURI.DEFAULT_REDIS_PORT));
        try (StatefulRedisConnection<String, String> connect = redisClient.connect()) {
            if (connect.isOpen()) {
                System.out.println("\nConnected to Redis\n");
            }
        } catch (Exception e) {
            throw new AppException("\nConnection failed\n", e);
        }
        return redisClient;
    }
}
