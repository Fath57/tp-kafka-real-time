package com.learn.kafka.service;

import com.learn.kafka.model.ExchangeRate;
import com.learn.kafka.producer.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRateScheduler {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateScheduler.class);

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private ExchangeRateStorageService storageService;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private List<String[]> currencyPairs;

    @Scheduled(cron = "0 * * * * *") // Exécution toutes les minutes
    public void fetchAndPublishExchangeRates() {
        logger.info("Starting scheduled exchange rate fetch");
        
        for (String[] pair : currencyPairs) {
            try {
                String baseCurrency = pair[0];
                String targetCurrency = pair[1];
                
                // Récupérer le taux de change
                ExchangeRate rate = exchangeRateService.getExchangeRate(baseCurrency, targetCurrency);
                
                // Publier sur Kafka
                messageProducer.sendMessage("exchange-rates", rate);
                
                // Sauvegarder dans Elasticsearch
                storageService.saveExchangeRate(rate);
                
                logger.info("Successfully processed exchange rate for {}/{}: {}", 
                    baseCurrency, targetCurrency, rate.getRate());
            } catch (Exception e) {
                logger.error("Error processing exchange rate for {}/{}: {}", 
                    pair[0], pair[1], e.getMessage());
            }
        }
        
        logger.info("Completed scheduled exchange rate fetch");
    }
} 