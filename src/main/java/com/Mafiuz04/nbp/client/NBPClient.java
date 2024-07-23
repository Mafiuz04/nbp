package com.Mafiuz04.nbp.client;

import com.Mafiuz04.nbp.model.CurrencyRateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "NBPClient", url = "api.nbp.pl/")
public interface NBPClient {

    @GetMapping("api/exchangerates/tables/{table}")
    List<CurrencyRateDTO> getAList(@PathVariable("table") String table);

    @GetMapping("api/exchangerates/tables/{table}/{date}")
    List<CurrencyRateDTO>  getAListByDate(@PathVariable("table") String table,@PathVariable("date") String date);
}
