package com.Mafiuz04.nbp.service;

import com.Mafiuz04.nbp.client.NBPClient;
import com.Mafiuz04.nbp.exception.NBPException;
import com.Mafiuz04.nbp.model.CurrencyRateDTO;
import com.Mafiuz04.nbp.model.RateDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class NBPService {

    private final NBPClient nbpClient;

    public List<RateDto> getListOfRates(LocalDate date) {
        if (date == null) {
            List<CurrencyRateDTO> listA = List.of(nbpClient.getAList());
            List<RateDto> ratesA = listA.stream()
                    .flatMap(rates -> Arrays.stream(listA.getLast().getRates()))
                    .toList();
            List<CurrencyRateDTO> listB = List.of(nbpClient.getBList());
            List<RateDto> ratesB = listA.stream()
                    .flatMap(rates -> Arrays.stream(listB.getLast().getRates()))
                    .toList();

            return Stream.of(ratesA, ratesB)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String format = date.format(dateTimeFormatter);
            List<CurrencyRateDTO> listAByDate = List.of(nbpClient.getAListByDate(format));
           return listAByDate.stream()
                    .flatMap(rates -> Arrays.stream(listAByDate.getLast().getRates()))
                    .toList();

        }
    }

    public RateDto getCurrency(String currency) {
        return getListOfRates(null).stream()
                .filter(rateDto -> rateDto.getCode().equals(currency.toUpperCase()))
                .findFirst().orElseThrow(() -> new NBPException("The selected currency does not exist in our system", HttpStatus.BAD_REQUEST));
    }
}
