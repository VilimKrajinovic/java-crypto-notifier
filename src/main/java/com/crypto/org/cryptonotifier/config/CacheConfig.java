package com.crypto.org.cryptonotifier.config;

import com.crypto.org.cryptonotifier.api.models.CryptoCurrencyInfo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Signal;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    public static final String CRYPTOS_CACHE_IDENTIFIER ="getCryptosCache";

    @Bean(name = CRYPTOS_CACHE_IDENTIFIER)
    public Cache<String, ? super Signal<? extends CryptoCurrencyInfo>> getCryptosCache(){
        return CacheBuilder.newBuilder()
                .expireAfterAccess(60, TimeUnit.SECONDS)
                .build();
    }
}
