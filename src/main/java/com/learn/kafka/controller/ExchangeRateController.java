package com.learn.kafka.controller;

import com.learn.kafka.model.ExchangeRate;
import com.learn.kafka.service.ExchangeRateStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/web/exchange-rates")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateStorageService storageService;

    @GetMapping
    public String getExchangeRatesPage(Model model) {
        List<ExchangeRate> rates = storageService.getAllExchangeRates();
        model.addAttribute("rates", rates);
        return "exchange-rates";
    }

    @GetMapping("/api")
    @ResponseBody
    public List<ExchangeRate> getExchangeRates() {
        return storageService.getAllExchangeRates();
    }
} 