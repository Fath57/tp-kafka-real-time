package com.learn.kafka.service;

import com.learn.kafka.model.ExchangeRate;
import com.learn.kafka.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class ExchangeRateStorageService {

    @Autowired
    private ExchangeRateRepository repository;

    public void saveExchangeRate(ExchangeRate rate) {
        repository.save(rate);
    }

    public ExchangeRate getLatestExchangeRate(String baseCurrency, String targetCurrency) {
        return repository.findByBaseCurrencyAndTargetCurrency(baseCurrency, targetCurrency);
    }

    public List<ExchangeRate> getAllExchangeRates() {
        List<ExchangeRate> rates = new ArrayList<>();
        repository.findAll().forEach(rates::add);
        return rates;
    }
}
