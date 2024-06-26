package com.javarush.boyarinov.service;

import com.javarush.boyarinov.config.SessionCreator;
import com.javarush.boyarinov.dao.CityDAO;
import com.javarush.boyarinov.dao.CountryDAO;
import com.javarush.boyarinov.entity.City;
import com.javarush.boyarinov.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class CityService {

    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final SessionCreator sessionCreator;

    public List<City> fetchData() {
        try (Session session = sessionCreator.getSession()) {
            Transaction tx = session.beginTransaction();
            try {
                List<City> allCities = new ArrayList<>();
                countryDAO.getAll();
                long totalCount = cityDAO.getTotalCount();
                int step = 500;
                for (int i = 0; i < totalCount; i += step) {
                    allCities.addAll(cityDAO.getItems(i, step));
                }
                tx.commit();
                return allCities;
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    public City getById(Integer id) {
        try (Session session = sessionCreator.getSession()) {
            Transaction tx = session.beginTransaction();
            try {
                City city = cityDAO.getById(id);
                tx.commit();
                return city;
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }

    public List<City> getListByIds(Set<Integer> ids) {
        try (Session session = sessionCreator.getSession()) {
            Transaction tx = session.beginTransaction();
            try {
                List<City> cityList = cityDAO.getListByIds(ids);
                tx.commit();
                return cityList;
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }
}
