package com.Mafiuz04.nbp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class CurrencyRateDTO {
    private String table;
    private String no;
    private LocalDate tradingDate;
    private LocalDate effectiveDate;
    private RateDto[] rates;
}
