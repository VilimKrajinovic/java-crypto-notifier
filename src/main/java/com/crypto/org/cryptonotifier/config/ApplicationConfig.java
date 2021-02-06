package com.crypto.org.cryptonotifier.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ApplicationConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WebClient getWebClientBuilder(@Value("${crypto.api-key:default}") String apiKey) {
        return WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build())
                .defaultHeader("X-CMC_PRO_API_KEY", apiKey)
                .baseUrl("pro-api.coinmarketcap.com")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().followRedirect(true)))
                .build();
    }
}
