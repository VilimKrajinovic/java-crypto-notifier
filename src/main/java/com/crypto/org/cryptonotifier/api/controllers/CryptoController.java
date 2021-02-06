package com.crypto.org.cryptonotifier.api.controllers;

import com.crypto.org.cryptonotifier.api.models.CryptoCurrencyInfo;
import com.crypto.org.cryptonotifier.api.service.CryptoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class CryptoController {

    private final CryptoService cryptoService;

    @GetMapping("/")
    public Mono<CryptoCurrencyInfo> getAllCryptos(){
        return cryptoService.getCryptos();
    }

    @GetMapping("/{symbol}")
    public Mono<CryptoCurrencyInfo> getDataForSymbol(@PathVariable String symbol){
        return cryptoService.getCryptoForSymbol(symbol);
    }
}
