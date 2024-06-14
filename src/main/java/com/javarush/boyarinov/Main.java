package com.javarush.boyarinov;

import com.javarush.boyarinov.config.RedisConnection;
import com.javarush.boyarinov.config.SessionCreator;
import com.javarush.boyarinov.dao.CityDAO;
import com.javarush.boyarinov.dao.CountryDAO;
import com.javarush.boyarinov.entity.City;
import com.javarush.boyarinov.redis.CityCountry;
import com.javarush.boyarinov.service.CityService;
import com.javarush.boyarinov.service.RedisService;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        SessionCreator sessionCreator = new SessionCreator();
        RedisConnection redisConnection = new RedisConnection();
        CountryDAO countryDAO = new CountryDAO(sessionCreator);
        CityDAO cityDAO = new CityDAO(sessionCreator);
        CityService cityService = new CityService(cityDAO, countryDAO, sessionCreator);
        RedisService redisService = new RedisService(redisConnection);

        List<City> cities = cityService.fetchData();
        List<CityCountry> cityCountryList = redisService.transformData(cities);
        redisService.pushToRedis(cityCountryList);

        sessionCreator.close();
    }
}
