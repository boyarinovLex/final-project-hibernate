package com.javarush.boyarinov.redis;

import com.javarush.boyarinov.entity.Continent;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CityCountry {

    private Integer id;

    private String name;

    private String district;

    private Integer population;

    private String countyCode;

    private String countyAlternativeCode;

    private String countyName;

    private Continent continent;

    private String countyRegion;

    private BigDecimal countySurfaceArea;

    private Integer countyPopulation;

    private List<Language> languages;

}
