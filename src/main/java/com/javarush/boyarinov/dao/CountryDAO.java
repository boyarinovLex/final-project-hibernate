package com.javarush.boyarinov.dao;

import com.javarush.boyarinov.config.SessionCreator;
import com.javarush.boyarinov.entity.Country;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CountryDAO {

    private final SessionCreator sessionCreator;

    public CountryDAO(SessionCreator sessionCreator) {
        this.sessionCreator = sessionCreator;
    }

    public List<Country> getAll() {
//        try(Session session = sessionCreator.getSession()) {
//            Transaction tx = session.beginTransaction();
            try {
                Session session = sessionCreator.getSession(); //TODO create nested transactions
                Query<Country> countryQuery = session.createQuery("SELECT c FROM Country c JOIN FETCH c.languages", Country.class);
                List<Country> countries = countryQuery.list();
//                tx.commit();
                return countries;
            } catch (Exception e) {
//                tx.rollback();
                throw new RuntimeException(e);
//            }
        }
    }
}

