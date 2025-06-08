package com.learn.kafka.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@Document(indexName = "exchange-rates", createIndex = true)
public class ExchangeRate {
    @Id
    private String id;
    
    @Field(type = FieldType.Keyword)
    private String baseCurrency;
    
    @Field(type = FieldType.Keyword)
    private String targetCurrency;
    
    @Field(type = FieldType.Double)
    private double rate;
    
    @Field(type = FieldType.Date)
    private Instant timestamp;

    public ExchangeRate(String baseCurrency, String targetCurrency, double rate, long timestamp) {
        this.id = baseCurrency + "_" + targetCurrency + "_" + timestamp;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.timestamp = Instant.ofEpochMilli(timestamp);
    }
}
