package com.Mafiuz04.nbp.client;

import com.Mafiuz04.nbp.model.CurrencyRateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.Date;

@FeignClient(name = "NBPClient", url = "http://api.nbp.pl/")
public interface NBPClient {

    @GetMapping("api/exchangerates/tables/A")
    CurrencyRateDTO[] getAList();

    @GetMapping("api/exchangerates/tables/B")
    CurrencyRateDTO[] getBList();

    @GetMapping("api/exchangerates/tables/A/{date}")
    CurrencyRateDTO[] getAListByDate(@PathVariable("date") String date);

    @GetMapping("api/exchangerates/tables/B/{date}")
    CurrencyRateDTO[] getBListByDate(@PathVariable("date") String date);

}
