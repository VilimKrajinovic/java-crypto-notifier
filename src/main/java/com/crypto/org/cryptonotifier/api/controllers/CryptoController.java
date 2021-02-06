package com.crypto.org.cryptonotifier.api.controllers;

import com.crypto.org.cryptonotifier.api.clients.CryptoInfoClient;
import com.crypto.org.cryptonotifier.api.models.CryptoCurrencyInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
public class CryptoController {

    private final CryptoInfoClient cryptoInfoClient;

    @GetMapping("/")
    public Flux<CryptoCurrencyInfo> getAllCryptos(){
        return cryptoInfoClient.getCryptos();
    }
}