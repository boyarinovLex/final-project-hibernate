package com.javarush.boyarinov.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Language {

    private String language;

    private Boolean isOfficial;

    private BigDecimal percentage;
}
