package com.Mafiuz04.nbp.controller;

import com.Mafiuz04.nbp.model.RateDto;
import com.Mafiuz04.nbp.service.NBPService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NBPControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NBPService nbpService;
    private List<RateDto> mockRates;

    @BeforeEach
    void setUp() {
        mockRates = Arrays.asList(
                new RateDto("US Dollar", "USD", new BigDecimal("4.00")),
                new RateDto("Euro", "EUR", new BigDecimal("4.50"))
        );
    }

    @Test
    void testHealthCheck() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/health").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Works fine"));
    }

    @Test
    void testGetListOfRates_NoDate() throws Exception {
        when(nbpService.getListOfRates(null)).thenReturn(mockRates);

        mockMvc.perform(MockMvcRequestBuilders.get("/rates").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].code").value("USD"))
                .andExpect(jsonPath("$[0].mid").value(4.0))
                .andExpect(jsonPath("$[0].code").value("EUR"))
                .andExpect(jsonPath("$[0].mid").value(4.5));
    }

    @Test
    void testGetListOfRates_WithDate() throws Exception {
        when(nbpService.getListOfRates(any(LocalDate.class))).thenReturn(mockRates);

        mockMvc.perform(MockMvcRequestBuilders.get("/rates/2024-12-10").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].code").value("USD"))
                .andExpect(jsonPath("$[0].mid").value(4.0))
                .andExpect(jsonPath("$[0].code").value("EUR"))
                .andExpect(jsonPath("$[0].mid").value(4.5));
    }

    @Test
    void testGetCurrency() throws Exception {
        RateDto usdRate = new RateDto("US Dollar", "USD", new BigDecimal("4.00"));
        when(nbpService.getCurrency(anyString())).thenReturn(usdRate);

        mockMvc.perform(MockMvcRequestBuilders.get("/rate/USD").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].code").value("USD"))
                .andExpect(jsonPath("$[0].mid").value(4.0));
    }
}

