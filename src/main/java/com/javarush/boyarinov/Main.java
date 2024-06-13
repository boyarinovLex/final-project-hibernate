package com.javarush.boyarinov;

import com.javarush.boyarinov.config.SessionCreator;
import com.javarush.boyarinov.dao.CityDAO;
import com.javarush.boyarinov.dao.CountryDAO;
import com.javarush.boyarinov.service.CityService;

public class Main {

    public static void main(String[] args) {

        SessionCreator sessionCreator = new SessionCreator();
        CountryDAO countryDAO = new CountryDAO(sessionCreator);
        CityDAO cityDAO = new CityDAO(sessionCreator);
        CityService cityService = new CityService(cityDAO, countryDAO, sessionCreator);

        cityService.fetchData();

        sessionCreator.close();
    }
}
