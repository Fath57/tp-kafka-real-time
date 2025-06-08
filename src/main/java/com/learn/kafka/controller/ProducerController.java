package com.learn.kafka.controller;

import com.learn.kafka.model.ExchangeRate;
import com.learn.kafka.producer.MessageProducer;
import com.learn.kafka.service.ExchangeRateService;
import com.learn.kafka.service.ExchangeRateStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private ExchangeRateStorageService storageService;

    @GetMapping("/exchange-rate")
    public ResponseEntity<ExchangeRate> getExchangeRate(
            @RequestParam("base") String baseCurrency,
            @RequestParam("target") String targetCurrency) {
        
        // get exchange rate from api
        ExchangeRate rate = exchangeRateService.getExchangeRate(baseCurrency, targetCurrency);
        // send rate to kafka
        messageProducer.sendMessage("exchange-rates", rate);
       //save rate in elasticsearch
        storageService.saveExchangeRate(rate);
       
        return ResponseEntity.ok(rate);
    }

    @GetMapping("/latest-exchange-rate")
    public ResponseEntity<ExchangeRate> getLatestExchangeRate(
            @RequestParam("base") String baseCurrency,
            @RequestParam("target") String targetCurrency) {
        
        ExchangeRate rate = storageService.getLatestExchangeRate(baseCurrency, targetCurrency);
        return ResponseEntity.ok(rate);
    }
}