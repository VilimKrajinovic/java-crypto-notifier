package com.crypto.org.cryptonotifier.config;

import com.crypto.org.cryptonotifier.api.models.map.CryptoCurrencyInfo;
import com.crypto.org.cryptonotifier.api.models.quotes.QuoteCryptoInfo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Signal;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    public static final String CRYPTOS_CACHE_IDENTIFIER ="getCryptosCache";
    public static final String QUOTE_CACHE_IDENTIFIER = "getQuotesCryptoCache";

    @Bean(name = CRYPTOS_CACHE_IDENTIFIER)
    public Cache<String, ? super Signal<? extends CryptoCurrencyInfo>> getCryptosCache(){
        return CacheBuilder.newBuilder()
                .expireAfterAccess(60, TimeUnit.SECONDS)
                .build();
    }

    @Bean(name = QUOTE_CACHE_IDENTIFIER)
    public Cache<String, ? super Signal<? extends QuoteCryptoInfo>> getQuotesCryptoCache(){
        return CacheBuilder.newBuilder()
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .build();
    }
}
