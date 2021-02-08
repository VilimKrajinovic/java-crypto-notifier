package com.crypto.org.cryptonotifier.api.clients;

import com.crypto.org.cryptonotifier.api.models.map.CryptoCurrencyInfo;
import com.crypto.org.cryptonotifier.api.models.quotes.QuoteCryptoInfo;
import com.crypto.org.cryptonotifier.api.service.CachedCryptoService;
import com.crypto.org.cryptonotifier.api.service.CryptoService;
import com.google.common.cache.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CachedCryptoServiceTest {

    private static final CryptoCurrencyInfo CRYPTO_INFO = new CryptoCurrencyInfo();
    private static final QuoteCryptoInfo QUOTE_CRYPTO_INFO = new QuoteCryptoInfo();

    @Mock
    CryptoService delegateService;

    @Mock
    Cache cryptosCache;

    @Mock
    Cache quotesCache;

    CachedCryptoService cachedCryptoService;

    @BeforeEach
    void init(){
        given(cryptosCache.asMap()).willReturn(new ConcurrentHashMap<>());
        given(quotesCache.asMap()).willReturn(new ConcurrentHashMap<>());
        cachedCryptoService = new CachedCryptoService(delegateService, cryptosCache, quotesCache);
    }

    @Test
    public void shouldDelegateOnCacheMiss() {
        given(delegateService.getCryptos())
                .willReturn(Mono.just(CRYPTO_INFO));
        given(delegateService.getQuotesForSymbol(any()))
                .willReturn(Mono.just(QUOTE_CRYPTO_INFO));
        String givenSymbol = "symbol";

        Mono<CryptoCurrencyInfo> actual = cachedCryptoService.getCryptos();
        Mono<QuoteCryptoInfo> actualQuote = cachedCryptoService.getQuotesForSymbol(givenSymbol);

        StepVerifier.create(actual)
                .expectNext(CRYPTO_INFO)
                .verifyComplete();

        StepVerifier.create(actualQuote)
                .expectNext(QUOTE_CRYPTO_INFO)
                .verifyComplete();
    }

    @Test
    public void shouldCacheValueFromDelegateAndReuseItLater() {
        given(delegateService.getCryptos())
                .willReturn(Mono.just(CRYPTO_INFO))
                .willReturn(Mono.error(new RuntimeException("Should have been cached")));
        given(delegateService.getQuotesForSymbol(any()))
                .willReturn(Mono.just(QUOTE_CRYPTO_INFO))
                .willReturn(Mono.error(new RuntimeException("Should have been cached")));
        String givenSymbol = "symbol";

        Mono<CryptoCurrencyInfo> first = cachedCryptoService.getCryptos();
        Mono<CryptoCurrencyInfo> second = cachedCryptoService.getCryptos();

        Mono<QuoteCryptoInfo> firstQuote = cachedCryptoService.getQuotesForSymbol(givenSymbol);
        Mono<QuoteCryptoInfo> secondQuote = cachedCryptoService.getQuotesForSymbol(givenSymbol);

        StepVerifier.create(first)
                .expectNext(CRYPTO_INFO)
                .verifyComplete();
        StepVerifier.create(second)
                .expectNext(CRYPTO_INFO)
                .verifyComplete();

        StepVerifier.create(firstQuote)
                .expectNext(QUOTE_CRYPTO_INFO)
                .verifyComplete();
        StepVerifier.create(secondQuote)
                .expectNext(QUOTE_CRYPTO_INFO)
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
