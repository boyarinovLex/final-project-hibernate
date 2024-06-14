package com.javarush.boyarinov.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.javarush.boyarinov.config.RedisConnection;
import com.javarush.boyarinov.entity.City;
import com.javarush.boyarinov.entity.Country;
import com.javarush.boyarinov.entity.CountryLanguage;
import com.javarush.boyarinov.exception.AppException;
import com.javarush.boyarinov.redis.CityCountry;
import com.javarush.boyarinov.redis.Language;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisService {

    private final RedisConnection redisConnection;

    public RedisService(RedisConnection redisConnection) {
        this.redisConnection = redisConnection;
    }

    public List<CityCountry> transformData(List<City> cities) {
        return cities.stream()
                .map(city -> {
                    Country country = city.getCountry();
                    Set<CountryLanguage> countryLanguages = country.getLanguages();
                    List<Language> languages = countryLanguages.stream()
                            .map(cl -> Language.builder()
                                    .language(cl.getLanguage())
                                    .isOfficial(cl.getIsOfficial())
                                    .percentage(cl.getPercentage())
                                    .build())
                            .collect(Collectors.toList());
                    return CityCountry.builder()
                            .id(city.getId())
                            .name(city.getName())
                            .district(city.getDistrict())
                            .population(city.getPopulation())
                            .countyCode(country.getCode())
                            .countyAlternativeCode(country.getAlternativeCode())
                            .countyName(country.getName())
                            .continent(country.getContinent())
                            .countyRegion(country.getRegion())
                            .countySurfaceArea(country.getSurfaceArea())
                            .countyPopulation(country.getPopulation())
                            .languages(languages)
                            .build();
                }).collect(Collectors.toList());
    }

    public void pushToRedis(List<CityCountry> cityCountries) {
        ObjectMapper mapper = new JsonMapper();
        try (RedisClient redisClient = redisConnection.prepareRedisClient();
             StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisCommands<String, String> sync = connection.sync();
            for (CityCountry cityCountry : cityCountries) {
                try {
                    sync.set(String.valueOf(cityCountry.getId()), mapper.writeValueAsString(cityCountry));
                } catch (JsonProcessingException e) {
                    throw new AppException(e);
                }
            }
        }
    }

}
