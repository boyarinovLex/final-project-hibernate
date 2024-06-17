package com.javarush.boyarinov;

import com.javarush.boyarinov.config.Container;
import com.javarush.boyarinov.config.SessionCreator;
import com.javarush.boyarinov.entity.City;
import com.javarush.boyarinov.redis.CityCountry;
import com.javarush.boyarinov.service.CityService;
import com.javarush.boyarinov.service.RedisService;

import java.util.List;
import java.util.Set;

public class AppStarter {

    private final Container container = new Container();
    private final CityService cityService = container.CITY_SERVICE;
    private final RedisService redisService = container.REDIS_SERVICE;
    private final SessionCreator sessionCreator = container.SESSION_CREATOR;


    public void run() {
        List<City> cities = cityService.fetchData();
        List<CityCountry> cityCountryList = redisService.transformData(cities);
        redisService.pushToRedis(cityCountryList);

        Set<Integer> ids = Set.of(1860, 1864, 1867, 1866, 1865);

        long startRedis = System.currentTimeMillis();
        redisService.getFromRedis(ids);
        long endRedis = System.currentTimeMillis();

        long startDB = System.currentTimeMillis();
        cityService.getListByIds(ids);
        long endDB = System.currentTimeMillis();

        System.out.printf("Query execution time:\nRedis:\t%d\nDatabase:\t%d\n", (endRedis - startRedis), (endDB - startDB));

        sessionCreator.close();
    }
}
