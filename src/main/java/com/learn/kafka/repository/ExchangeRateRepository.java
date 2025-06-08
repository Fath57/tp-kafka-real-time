package com.learn.kafka.repository;

import com.learn.kafka.model.ExchangeRate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends ElasticsearchRepository<ExchangeRate, String> {
    ExchangeRate findByBaseCurrencyAndTargetCurrency(String baseCurrency, String targetCurrency);
}
