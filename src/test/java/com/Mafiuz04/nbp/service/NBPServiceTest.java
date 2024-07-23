package com.Mafiuz04.nbp.service;

import com.Mafiuz04.nbp.client.NBPClient;
import com.Mafiuz04.nbp.exception.NBPException;
import com.Mafiuz04.nbp.model.CurrencyRateDTO;
import com.Mafiuz04.nbp.model.RateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

import static org.mockito.Mockito.*;

public class NBPServiceTest {

    private NBPClient nbpClient;
    private NBPService nbpService;

    @BeforeEach
    void setUp() {
        this.nbpClient = Mockito.mock(NBPClient.class);
        this.nbpService = new NBPService(nbpClient);
    }

    @Test
    void testGetListOfRates_NoDate() {
        List<CurrencyRateDTO> list = createAList();
        LocalDate date = LocalDate.of(2023, 12, 20);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = date.format(dateTimeFormatter);

        when(nbpClient.getAListByDate("A", format)).thenReturn(list);

        List<RateDto> result = nbpService.getListOfRates(date);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.size());
        Assertions.assertTrue(result.stream().anyMatch(rate -> "USD".equals(rate.getCode())));
        Assertions.assertTrue(result.stream().anyMatch(rate -> "EUR".equals(rate.getCode())));
    }

    @Test
    void testGetListOfRates_WithDate() {
        LocalDate date = LocalDate.of(2023, 7, 20);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<CurrencyRateDTO> list = createAList();

        when(nbpClient.getAListByDate("A", formattedDate)).thenReturn(list);

        List<RateDto> result = nbpService.getListOfRates(date);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.size());
        Assertions.assertTrue(result.stream().anyMatch(rate -> "USD".equals(rate.getCode())));
        Assertions.assertTrue(result.stream().anyMatch(rate -> "EUR".equals(rate.getCode())));
    }

    @Test
    void testGetCurrency_Exists() {
        List<CurrencyRateDTO> list = createAList();

        when(nbpClient.getAList("A")).thenReturn(list);
        when(nbpClient.getAList("B")).thenReturn(list);

        RateDto result = nbpService.getCurrency("USD");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("USD", result.getCode());
        Assertions.assertEquals(new BigDecimal("4.00"), result.getMid());
    }

    @Test
    void testGetCurrency_NotExists() {
        List<CurrencyRateDTO> list = createAList();

        when(nbpClient.getAList("A")).thenReturn(list);
        when(nbpClient.getAList("B")).thenReturn(list);

        NBPException exception = Assertions.assertThrows(NBPException.class, () -> {
            nbpService.getCurrency("PLN");
        });

        Assertions.assertEquals("The selected currency does not exist in our system", exception.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    private List<CurrencyRateDTO> createAList() {
        List<CurrencyRateDTO> list = new ArrayList<>();
        CurrencyRateDTO currencyRateDTOB = new CurrencyRateDTO(
                "B", "002", LocalDate.now(), LocalDate.now(),
                List.of(
                        new RateDto("British Pound", "GBP", new BigDecimal("5.00")),
                        new RateDto("Swiss Franc", "CHF", new BigDecimal("4.20")),
                        new RateDto("American Dolar", "USD", new BigDecimal("4.00")),
                        new RateDto("Euro", "EUR", new BigDecimal("4.50")))
        );
        list.add(currencyRateDTOB);
        return list;

    }
}



