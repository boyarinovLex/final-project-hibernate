package com.javarush.boyarinov.dao;

import com.javarush.boyarinov.config.SessionCreator;
import com.javarush.boyarinov.entity.City;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class CityDAO {

    private final SessionCreator sessionCreator;

    public List<City> getItems(int offset, int limit) {
        Session session = sessionCreator.getSession();
        Query<City> cityQuery = session.createQuery("FROM City", City.class);
        cityQuery.setFirstResult(offset);
        cityQuery.setMaxResults(limit);
        return cityQuery.list();
    }

    public long getTotalCount() {
        Session session = sessionCreator.getSession();
        Query<Long> query = session.createQuery("SELECT COUNT(c) FROM City c", Long.class);
        return query.uniqueResult();
    }
}
