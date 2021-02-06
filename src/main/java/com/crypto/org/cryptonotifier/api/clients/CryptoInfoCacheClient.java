package com.crypto.org.cryptonotifier.api.clients;

import com.crypto.org.cryptonotifier.api.models.CryptoCurrencyInfo;
import com.crypto.org.cryptonotifier.api.service.CryptoInfoService;
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
public class CryptoInfoCacheClient implements CryptoInfoService {

    private static final String CRYPTO_CACHE_KEY = "cryptos";
    private final CryptoInfoService delegate;
    private final Map<Object, ? super Signal<? extends CryptoCurrencyInfo>> cache;

    public CryptoInfoCacheClient(
            CryptoInfoService delegate,
            @Qualifier(CacheConfig.CRYPTOS_CACHE_IDENTIFIER) Cache<Object, ? super Signal<? extends CryptoCurrencyInfo>> cache) {
        this.delegate = delegate;
        this.cache = cache.asMap();
    }

    @Override
    public Mono<CryptoCurrencyInfo> getCryptos() {
        return CacheMono.lookup(cache, CRYPTO_CACHE_KEY)
                .onCacheMissResume(delegate::getCryptos);
    }
}
