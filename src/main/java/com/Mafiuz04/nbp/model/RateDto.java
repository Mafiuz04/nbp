package com.Mafiuz04.nbp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RateDto {
    private String currency;
    private String code;
    private BigDecimal mid;
}
