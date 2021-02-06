package com.crypto.org.cryptonotifier.api.clients;

import com.crypto.org.cryptonotifier.api.models.CryptoCurrencyInfo;
import com.crypto.org.cryptonotifier.api.service.CachedCryptoService;
import com.crypto.org.cryptonotifier.api.service.CryptoService;
import com.google.common.cache.Cache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CachedCryptoServiceTest {

    private static final CryptoCurrencyInfo CRYPTO_INFO = new CryptoCurrencyInfo();

    @Mock
    CryptoService delegateService;

    @Mock
    Cache cryptosCache;

    CachedCryptoService cachedCryptoService;

    @Before
    public void setup(){
        given(cryptosCache.asMap()).willReturn(new ConcurrentHashMap<>());
        cachedCryptoService = new CachedCryptoService(delegateService, cryptosCache);
    }

    @Test
    public void shouldDelegateOnCacheMiss() {
        given(delegateService.getCryptos())
                .willReturn(Mono.just(CRYPTO_INFO));

        Mono<CryptoCurrencyInfo> actual = cachedCryptoService.getCryptos();

        StepVerifier.create(actual)
                .expectNext(CRYPTO_INFO)
                .verifyComplete();
    }

    @Test
    public void shouldCacheValueFromDelegateAndReuseItLater() {
        given(delegateService.getCryptos())
                .willReturn(Mono.just(CRYPTO_INFO))
                .willReturn(Mono.error(new RuntimeException("Should have been cached")));

        Mono<CryptoCurrencyInfo> first = cachedCryptoService.getCryptos();
        Mono<CryptoCurrencyInfo> second = cachedCryptoService.getCryptos();

        StepVerifier.create(first)
                .expectNext(CRYPTO_INFO)
                .verifyComplete();
        StepVerifier.create(second)
                .expectNext(CRYPTO_INFO)
                .verifyComplete();
    }

    @Test
    public void shouldCacheIndividualSymbolValueFromDelegateAndReuseItLater() {
        given(delegateService.getCryptoForSymbol(any()))
                .willReturn(Mono.just(CRYPTO_INFO))
                .willReturn(Mono.error(new RuntimeException("Should have been cached")));
        String givenSymbol = "symbol";

        Mono<CryptoCurrencyInfo> first = cachedCryptoService.getCryptoForSymbol(givenSymbol);
        Mono<CryptoCurrencyInfo> second = cachedCryptoService.getCryptoForSymbol(givenSymbol);

        StepVerifier.create(first)
                .expectNext(CRYPTO_INFO)
                .verifyComplete();
        StepVerifier.create(second)
                .expectNext(CRYPTO_INFO)
                .verifyComplete();
    }

}
