package com.crypto.org.cryptonotifier.api.service;

import com.crypto.org.cryptonotifier.api.models.CryptoCurrencyInfo;
import reactor.core.publisher.Mono;

public interface CryptoService {
    Mono<CryptoCurrencyInfo> getCryptos();
    Mono<CryptoCurrencyInfo> getCryptoForSymbol(String symbol);
}
