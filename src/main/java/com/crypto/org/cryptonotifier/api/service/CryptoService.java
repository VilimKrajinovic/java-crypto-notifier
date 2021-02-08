package com.crypto.org.cryptonotifier.api.service;

import com.crypto.org.cryptonotifier.api.models.map.CryptoCurrencyInfo;
import com.crypto.org.cryptonotifier.api.models.quotes.QuoteCryptoInfo;
import reactor.core.publisher.Mono;

public interface CryptoService {
    Mono<CryptoCurrencyInfo> getCryptos();
    Mono<CryptoCurrencyInfo> getCryptoForSymbol(String symbol);
    Mono<QuoteCryptoInfo> getQuotesForSymbol(String symbol);

}
