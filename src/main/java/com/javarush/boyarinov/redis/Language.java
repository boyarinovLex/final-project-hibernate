package com.javarush.boyarinov.redis;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Language {

    private String language;

    private Boolean isOfficial;

    private BigDecimal percentage;
}
