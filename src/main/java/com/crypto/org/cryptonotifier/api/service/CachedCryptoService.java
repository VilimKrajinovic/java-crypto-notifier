package com.crypto.org.cryptonotifier.api.service;

import com.crypto.org.cryptonotifier.api.models.map.CryptoCurrencyInfo;
import com.crypto.org.cryptonotifier.api.models.quotes.QuoteCryptoInfo;
import com.crypto.org.cryptonotifier.config.CacheConfig;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.Map;

@Service
@Primary
public class CachedCryptoService implements CryptoService {

    private static final String CRYPTO_CACHE_KEY = "allCryptos";
    private final CryptoService delegate;
    private final Map<String, ? super Signal<? extends CryptoCurrencyInfo>> cache;
    private final Map<String, ? super Signal<? extends QuoteCryptoInfo>> quoteCache;

    public CachedCryptoService(
            CryptoService delegate,
            @Qualifier(CacheConfig.CRYPTOS_CACHE_IDENTIFIER) Cache<String, ? super Signal<? extends CryptoCurrencyInfo>> cache,
            @Qualifier(CacheConfig.QUOTE_CACHE_IDENTIFIER) Cache<String, ? super Signal <? extends QuoteCryptoInfo>> quoteCache
    ) {
        this.delegate = delegate;
        this.cache = cache.asMap();
        this.quoteCache = quoteCache.asMap();
    }

    @Override
    public Mono<CryptoCurrencyInfo> getCryptos() {
        return CacheMono.lookup(cache, CRYPTO_CACHE_KEY)
                .onCacheMissResume(delegate::getCryptos);
    }

    @Override
    public Mono<CryptoCurrencyInfo> getCryptoForSymbol(String symbol) {
        return CacheMono.lookup(cache, symbol)
                .onCacheMissResume(() -> delegate.getCryptoForSymbol(symbol));
    }

    @Override
    public Mono<QuoteCryptoInfo> getQuotesForSymbol(String symbol) {
        return CacheMono.lookup(quoteCache, symbol)
                .onCacheMissResume(() -> delegate.getQuotesForSymbol(symbol));
    }
}
