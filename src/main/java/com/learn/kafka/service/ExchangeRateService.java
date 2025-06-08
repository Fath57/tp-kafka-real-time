package com.learn.kafka.service;

import com.learn.kafka.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateService {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD";
    private final RestTemplate restTemplate;

    @Autowired
    public ExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExchangeRate getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            // Appel à l'API externe
            Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
            
            if (response == null || response.get("rates") == null) {
                throw new RuntimeException("Failed to fetch exchange rates");
            }

            @SuppressWarnings("unchecked")
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            double rate = rates.getOrDefault(targetCurrency, 1.0);

            return new ExchangeRate(
                baseCurrency,
                targetCurrency,
                rate,
                Instant.now().toEpochMilli()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error fetching exchange rate: " + e.getMessage(), e);
        }
    }
}
