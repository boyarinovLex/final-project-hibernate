package com.javarush.boyarinov.config;

import com.javarush.boyarinov.dao.CityDAO;
import com.javarush.boyarinov.dao.CountryDAO;
import com.javarush.boyarinov.service.CityService;
import com.javarush.boyarinov.service.RedisService;

public final class Container {

    public final SessionCreator SESSION_CREATOR = new SessionCreator();
    public final RedisConnection REDIS_CONNECTION = new RedisConnection();
    public final CountryDAO COUNTRY_DAO = new CountryDAO(SESSION_CREATOR);
    public final CityDAO CITY_DAO = new CityDAO(SESSION_CREATOR);
    public final CityService CITY_SERVICE = new CityService(CITY_DAO, COUNTRY_DAO, SESSION_CREATOR);
    public final RedisService REDIS_SERVICE = new RedisService(REDIS_CONNECTION);

}
