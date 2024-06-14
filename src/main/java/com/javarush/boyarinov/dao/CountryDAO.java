package com.javarush.boyarinov.dao;

import com.javarush.boyarinov.config.SessionCreator;
import com.javarush.boyarinov.entity.Country;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class CountryDAO {

    private final SessionCreator sessionCreator;

    public List<Country> getAll() {
        Session session = sessionCreator.getSession();
        Query<Country> countryQuery = session.createQuery("SELECT c FROM Country c JOIN FETCH c.languages", Country.class);
        return countryQuery.list();
    }
}

