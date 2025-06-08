package com.learn.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ExchangeRateConfig {

    @Bean
    public List<String[]> currencyPairs() {
        return Arrays.asList(
            new String[]{"USD", "EUR"},
            new String[]{"USD", "GBP"},
            new String[]{"USD", "JPY"},
            new String[]{"EUR", "USD"},
            new String[]{"EUR", "GBP"},
            new String[]{"EUR", "JPY"}
        );
    }
} 