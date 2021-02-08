package com.crypto.org.cryptonotifier.api.service;

import com.crypto.org.cryptonotifier.api.models.quotes.QuoteCryptoInfo;
import com.crypto.org.cryptonotifier.api.models.map.CryptoCurrencyInfo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Log4j2
@AllArgsConstructor
public class WebCryptoService implements CryptoService {

    private static final String VERSION_PREFIX = "/v1";
    private static final String CRYPTO_PREFIX = VERSION_PREFIX + "/cryptocurrency";
    private static final String MAP_ENDPOINT = CRYPTO_PREFIX + "/map";
    private static final String LATEST_QUOTES_ENDPOINT = CRYPTO_PREFIX + "/quotes/latest";

    private final WebClient webClient;

    @Override
    public Mono<CryptoCurrencyInfo> getCryptos() {
        return webClient.get().uri(uriBuilder -> uriBuilder.path(MAP_ENDPOINT)
                .queryParam("sort", "id")
                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CryptoCurrencyInfo.class)
                .doOnError(log::error);
    }

    @Override
    public Mono<CryptoCurrencyInfo> getCryptoForSymbol(String symbol) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path(MAP_ENDPOINT)
                .queryParam("symbol", symbol)
                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CryptoCurrencyInfo.class)
                .doOnError(log::error);
    }

    @Override
    public Mono<QuoteCryptoInfo> getQuotesForSymbol(String symbol) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path(LATEST_QUOTES_ENDPOINT)
                .queryParam("symbol", symbol)
                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(QuoteCryptoInfo.class)
                .doOnError(log::error);
    }
}
