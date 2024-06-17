package com.javarush.boyarinov.dao;

import com.javarush.boyarinov.config.SessionCreator;
import com.javarush.boyarinov.entity.City;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

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

    public City getById(Integer id) {
        Session session = sessionCreator.getSession();
        Query<City> query = session.createQuery("SELECT c FROM City c JOIN FETCH c.country WHERE c.id = :ID", City.class);
        query.setParameter("ID", id);
        return query.uniqueResult();
    }

    public List<City> getListByIds(Set<Integer> ids) {
        Session session = sessionCreator.getSession();
        Query<City> query = session.createQuery("SELECT c FROM City c JOIN FETCH c.country WHERE c.id IN (:IDS)", City.class);
        query.setParameter("IDS", ids);
        return query.list();
    }
}
