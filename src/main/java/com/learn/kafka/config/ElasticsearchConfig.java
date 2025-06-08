package com.learn.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.boot.CommandLineRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import java.util.Map;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.learn.kafka.repository")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.client.rest.uris}")
    private String elasticsearchUri;

    @Value("${spring.elasticsearch.client.rest.username}")
    private String username;

    @Value("${spring.elasticsearch.client.rest.password}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
            .connectedTo(elasticsearchUri)
            .withBasicAuth(username, password)
            .build();
    }

    @Bean
    public CommandLineRunner init(ElasticsearchOperations elasticsearchOperations) {
        return args -> {
            try {
                IndexOperations indexOperations = elasticsearchOperations.indexOps(IndexCoordinates.of("exchange-rates"));
                
                if (!indexOperations.exists()) {
                    Map<String, Object> mapping = Map.of(
                        "properties", Map.of(
                            "id", Map.of("type", "keyword"),
                            "baseCurrency", Map.of("type", "keyword"),
                            "targetCurrency", Map.of("type", "keyword"),
                            "rate", Map.of("type", "float"),
                            "timestamp", Map.of("type", "date")
                        )
                    );
                    
                    indexOperations.create(mapping);
                    System.out.println("Index exchange-rates créé avec succès");
                }
            } catch (Exception e) {
                System.err.println("Erreur lors de la création de l'index : " + e.getMessage());
            }
        };
    }
}