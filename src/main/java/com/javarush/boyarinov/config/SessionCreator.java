package com.javarush.boyarinov.config;

import com.javarush.boyarinov.entity.City;
import com.javarush.boyarinov.entity.Country;
import com.javarush.boyarinov.entity.CountryLanguage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionCreator {

    private final SessionFactory sessionFactory;
    private Session session = null;

    public SessionCreator() {
        sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(CountryLanguage.class)
                .buildSessionFactory();
    }

    public Session getSession() {
        session = session == null || !session.isOpen()
                ? sessionFactory.openSession()
                : session;
        return session;
    }

    public void close() {
        sessionFactory.close();
    }
}
