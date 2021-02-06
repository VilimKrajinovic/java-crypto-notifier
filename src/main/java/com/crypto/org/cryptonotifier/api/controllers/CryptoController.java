package com.crypto.org.cryptonotifier.api.controllers;

import com.crypto.org.cryptonotifier.api.models.CryptoCurrencyInfo;
import com.crypto.org.cryptonotifier.api.service.CryptoInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class CryptoController {

    private final CryptoInfoService cryptoInfoService;

    @GetMapping("/")
    public Mono<CryptoCurrencyInfo> getAllCryptos(){
        return cryptoInfoService.getCryptos();
    }
}
