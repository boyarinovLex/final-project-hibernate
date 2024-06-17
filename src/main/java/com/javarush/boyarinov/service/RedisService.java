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
import io.lettuce.core.RedisException;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RedisService {

    private final RedisConnection redisConnection;
    private final ObjectMapper mapper = new JsonMapper();

    public List<CityCountry> transformData(List<City> cities) {
        return cities.stream()
                .map(city -> {
                    Country country = city.getCountry();
                    Set<CountryLanguage> countryLanguages = country.getLanguages();
                    List<Language> languages = countryLanguages.stream()
                            .map(cl ->
                            {
                                Language language = new Language();
                                language.setLanguage(cl.getLanguage());
                                language.setIsOfficial(cl.getIsOfficial());
                                language.setPercentage(cl.getPercentage());
                                return language;
                            })
                            .collect(Collectors.toList());
                    CityCountry cityCountry = new CityCountry();
                    cityCountry.setId(city.getId());
                    cityCountry.setName(city.getName());
                    cityCountry.setDistrict(city.getDistrict());
                    cityCountry.setPopulation(city.getPopulation());
                    cityCountry.setCountyCode(country.getCode());
                    cityCountry.setCountyAlternativeCode(country.getAlternativeCode());
                    cityCountry.setCountyName(country.getName());
                    cityCountry.setContinent(country.getContinent());
                    cityCountry.setCountyRegion(country.getRegion());
                    cityCountry.setCountySurfaceArea(country.getSurfaceArea());
                    cityCountry.setCountyPopulation(country.getPopulation());
                    cityCountry.setLanguages(languages);
                    return cityCountry;
                }).collect(Collectors.toList());
    }

    public void pushToRedis(List<CityCountry> cityCountries) {
        try (RedisClient redisClient = redisConnection.prepareRedisClient();
             StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisCommands<String, String> sync = connection.sync();
            for (CityCountry cityCountry : cityCountries) {
                try {
                    sync.set(String.valueOf(cityCountry.getId()), mapper.writeValueAsString(cityCountry));
                } catch (JsonProcessingException | RedisException e) {
                    throw new AppException(e);
                }
            }
        }
    }

    public CityCountry getFromRedis(Integer id) {
        try (RedisClient redisClient = redisConnection.prepareRedisClient();
             StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisCommands<String, String> sync = connection.sync();
            try {
                String value = sync.get(String.valueOf(id));
                return mapper.readValue(value, CityCountry.class);
            } catch (JsonProcessingException | RedisException e) {
                throw new AppException(e);
            }
        }
    }

    public List<CityCountry> getFromRedis(Set<Integer> ids) {
        try (RedisClient redisClient = redisConnection.prepareRedisClient();
             StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisCommands<String, String> sync = connection.sync();
            try {
                List<CityCountry> cityCountries = new ArrayList<>();
                for (Integer id : ids) {
                    String value = sync.get(String.valueOf(id));
                    cityCountries.add(mapper.readValue(value, CityCountry.class));
                }
                return cityCountries;
            } catch (JsonProcessingException | RedisException e) {
                throw new AppException(e);
            }
        }
    }

}
