package com.crypto.org.cryptonotifier.api.clients;

import com.crypto.org.cryptonotifier.api.models.CryptoCurrencyInfo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Log4j2
@AllArgsConstructor
public class CryptoInfoClient {

    private final WebClient webClient;

    public Flux<CryptoCurrencyInfo> getCryptos() {
        return webClient.get().uri("/v1/cryptocurrency/map")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(CryptoCurrencyInfo.class)
                .doOnError(log::error)
                .onErrorReturn(CryptoCurrencyInfo.EMPTY_CURRENCY_INFO);
    }
}
