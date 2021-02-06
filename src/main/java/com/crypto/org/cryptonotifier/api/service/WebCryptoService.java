package com.crypto.org.cryptonotifier.api.service;

import com.crypto.org.cryptonotifier.api.models.CryptoCurrencyInfo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

@Service
@Log4j2
@AllArgsConstructor
public class WebCryptoService implements CryptoService {

    private final WebClient webClient;

    @Override
    public Mono<CryptoCurrencyInfo> getCryptos() {
        return webClient.get().uri("/v1/cryptocurrency/map")
                .accept(MediaType.APPLICATION_JSON)
                .attribute("sort", "id")
                .retrieve()
                .bodyToMono(CryptoCurrencyInfo.class)
                .doOnError(log::error)
                .onErrorReturn(CryptoCurrencyInfo.EMPTY_CURRENCY_INFO);
    }

    @Override
    public Mono<CryptoCurrencyInfo> getCryptoForSymbol(String symbol) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path("/v1/cryptocurrency/map")
                .queryParam("symbol", symbol)
                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CryptoCurrencyInfo.class)
                .doOnError(log::error)
                .onErrorReturn(CryptoCurrencyInfo.EMPTY_CURRENCY_INFO);
    }
}
