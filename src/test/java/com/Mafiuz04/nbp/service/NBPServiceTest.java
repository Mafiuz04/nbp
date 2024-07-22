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
import java.util.List;

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
        CurrencyRateDTO currencyRateDTOA = new CurrencyRateDTO(
                "A", "001", LocalDate.now(), LocalDate.now(),
                new RateDto[]{
                        new RateDto("US Dollar", "USD", new BigDecimal("4.00")),
                        new RateDto("Euro", "EUR", new BigDecimal("4.50"))
                }
        );

        CurrencyRateDTO currencyRateDTOB = new CurrencyRateDTO(
                "B", "002", LocalDate.now(), LocalDate.now(),
                new RateDto[]{
                        new RateDto("British Pound", "GBP", new BigDecimal("5.00")),
                        new RateDto("Swiss Franc", "CHF", new BigDecimal("4.20"))
                }
        );
        CurrencyRateDTO[] arrayA = new CurrencyRateDTO[1];
        arrayA[0] = currencyRateDTOA;
        CurrencyRateDTO[] arrayB = new CurrencyRateDTO[1];
        arrayB[0] = currencyRateDTOA;

        when(nbpClient.getAList()).thenReturn(arrayA);
        when(nbpClient.getBList()).thenReturn(arrayB);

        List<RateDto> result = nbpService.getListOfRates(null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.size());
        Assertions.assertTrue(result.stream().anyMatch(rate -> "USD".equals(rate.getCode())));
        Assertions.assertTrue(result.stream().anyMatch(rate -> "EUR".equals(rate.getCode())));
    }

    @Test
    void testGetListOfRates_WithDate() {
        LocalDate date = LocalDate.of(2023, 7, 20);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        CurrencyRateDTO currencyRateDTO = new CurrencyRateDTO(
                "A", "003", LocalDate.now(), LocalDate.now(),
                new RateDto[]{
                        new RateDto("US Dollar", "USD", new BigDecimal("4.00")),
                        new RateDto("Euro", "EUR", new BigDecimal("4.50"))
                }
        );
        CurrencyRateDTO[] array = new CurrencyRateDTO[1];
        array[0] = currencyRateDTO;

        when(nbpClient.getAListByDate(formattedDate)).thenReturn(array);

        List<RateDto> result = nbpService.getListOfRates(date);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().anyMatch(rate -> "USD".equals(rate.getCode())));
        Assertions.assertTrue(result.stream().anyMatch(rate -> "EUR".equals(rate.getCode())));
    }

    @Test
    void testGetCurrency_Exists() {
        CurrencyRateDTO currencyRateDTO = new CurrencyRateDTO(
                "A", "004", LocalDate.now(), LocalDate.now(),
                new RateDto[]{
                        new RateDto("US Dollar", "USD", new BigDecimal("4.00")),
                        new RateDto("Euro", "EUR", new BigDecimal("4.50"))
                }
        );
        CurrencyRateDTO[] array = new CurrencyRateDTO[1];
        array[0] = currencyRateDTO;

        when(nbpClient.getAList()).thenReturn(array);
        when(nbpClient.getBList()).thenReturn(array);

        RateDto result = nbpService.getCurrency("USD");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("USD", result.getCode());
        Assertions.assertEquals(new BigDecimal("4.00"), result.getMid());
    }

    @Test
    void testGetCurrency_NotExists() {
        CurrencyRateDTO currencyRateDTO = new CurrencyRateDTO(
                "A", "004", LocalDate.now(), LocalDate.now(),
                new RateDto[]{
                        new RateDto("US Dollar", "USD", new BigDecimal("4.00")),
                        new RateDto("Euro", "EUR", new BigDecimal("4.50"))
                }
        );
        CurrencyRateDTO[] array = new CurrencyRateDTO[1];
        array[0] = currencyRateDTO;

        when(nbpClient.getAList()).thenReturn(array);
        when(nbpClient.getBList()).thenReturn(array);

        NBPException exception = Assertions.assertThrows(NBPException.class, () -> {
            nbpService.getCurrency("PLN");
        });

        Assertions.assertEquals("The selected currency does not exist in our system", exception.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}



