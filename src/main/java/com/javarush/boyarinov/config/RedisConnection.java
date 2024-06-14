package com.javarush.boyarinov.config;

import com.javarush.boyarinov.constant.Constant;
import com.javarush.boyarinov.exception.AppException;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public class RedisConnection {

    public RedisClient prepareRedisClient() {
        RedisClient redisClient = RedisClient.create(RedisURI.create(Constant.HOST_FOR_REDIS, RedisURI.DEFAULT_REDIS_PORT));
        try (StatefulRedisConnection<String, String> connect = redisClient.connect()) {
            if (connect.isOpen()) {
                System.out.println(Constant.REDIS + Constant.CONNECTED_IS_GOOD);
            }
        } catch (Exception e) {
            throw new AppException(Constant.REDIS + Constant.CONNECTED_IS_BAD, e);
        }
        return redisClient;
    }
}
