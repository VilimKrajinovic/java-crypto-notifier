package com.crypto.org.cryptonotifier.api.service;

import com.crypto.org.cryptonotifier.api.models.CryptoCurrencyInfo;
import reactor.core.publisher.Mono;

public interface CryptoInfoService {
    Mono<CryptoCurrencyInfo> getCryptos();
}
