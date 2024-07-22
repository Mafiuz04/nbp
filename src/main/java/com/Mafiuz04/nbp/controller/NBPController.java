package com.Mafiuz04.nbp.controller;

import com.Mafiuz04.nbp.model.RateDto;
import com.Mafiuz04.nbp.service.NBPService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class NBPController {

    private final NBPService nbpService;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Works fine");
    }

    @GetMapping(value = {"/rates", "/rates/{date}"})
    public List<RateDto> getListOfRates(@PathVariable(required = false) LocalDate date) {
        return nbpService.getListOfRates(date);
    }

    @GetMapping("/rate/{currency}")
    public RateDto getCurrency(@PathVariable String currency) {
        return nbpService.getCurrency(currency);
    }
}
