package com.javarush.boyarinov.dao;

import com.javarush.boyarinov.config.SessionCreator;
import com.javarush.boyarinov.entity.City;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CityDAO {

    private final SessionCreator sessionCreator;

    public CityDAO(SessionCreator sessionCreator) {
        this.sessionCreator = sessionCreator;
    }

    public List<City> getItems(int offset, int limit) {
//        try (Session session = sessionCreator.getSession()) {
//            Transaction tx = session.beginTransaction();
        try {
            Session session = sessionCreator.getSession(); //TODO delete
            Query<City> cityQuery = session.createQuery("FROM City", City.class);
            cityQuery.setFirstResult(offset);
            cityQuery.setMaxResults(limit);
            List<City> cities = cityQuery.list();
//                tx.commit();
            return cities;
        } catch (Exception e) {
//                tx.rollback();
            throw new RuntimeException(e);
        }
//        }
    }

    public long getTotalCount() {
//        try(Session session = sessionCreator.getSession()) {
//            Transaction tx = session.beginTransaction();
        try {
            Session session = sessionCreator.getSession(); //TODO delete
            Query<Long> query = session.createQuery("SELECT COUNT(c) FROM City c", Long.class);
            long totalCount = query.uniqueResult();
//                tx.commit();
            return totalCount;
        } catch (Exception e) {
//                tx.rollback();
            throw new RuntimeException(e);
        }
//        }
    }
}
