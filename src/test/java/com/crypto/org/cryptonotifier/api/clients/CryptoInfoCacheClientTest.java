package com.crypto.org.cryptonotifier.api.clients;

import com.crypto.org.cryptonotifier.api.models.CryptoCurrencyInfo;
import com.crypto.org.cryptonotifier.api.service.CryptoInfoService;
import com.google.common.cache.Cache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CryptoInfoCacheClientTest {

    private static final CryptoCurrencyInfo CRYPTO_INFO = new CryptoCurrencyInfo();

    @Mock
    CryptoInfoService delegateService;

    @Mock
    Cache cryptosCache;

    CryptoInfoCacheClient cachedClient;

    @Before
    public void setup(){
        given(cryptosCache.asMap()).willReturn(new ConcurrentHashMap<>());
        cachedClient = new CryptoInfoCacheClient(delegateService, cryptosCache);
    }

    @Test
    public void shouldDelegateOnCacheMiss() {
        given(delegateService.getCryptos())
                .willReturn(Mono.just(CRYPTO_INFO));

        Mono<CryptoCurrencyInfo> actual = cachedClient.getCryptos();

        StepVerifier.create(actual)
                .expectNext(CRYPTO_INFO)
                .verifyComplete();
    }

    @Test
    public void shouldCacheValueFromDelegateAndReuseItLater() {
        given(delegateService.getCryptos())
                .willReturn(Mono.just(CRYPTO_INFO))
                .willReturn(Mono.error(new RuntimeException("Should have been cached")));

        Mono<CryptoCurrencyInfo> first = cachedClient.getCryptos();
        Mono<CryptoCurrencyInfo> second = cachedClient.getCryptos();

        StepVerifier.create(first)
                .expectNext(CRYPTO_INFO)
                .verifyComplete();
        StepVerifier.create(second)
                .expectNext(CRYPTO_INFO)
                .verifyComplete();
    }

}
