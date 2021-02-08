package com.crypto.org.cryptonotifier.api.models.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CryptoCurrencyInfo {

    public static final CryptoCurrencyInfo EMPTY_CURRENCY_INFO = new CryptoCurrencyInfo();

    @JsonProperty("data")
    private List<CryptoData> data;

    @Data
    public static class CryptoData{
        @JsonProperty("id")
        private int id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("symbol")
        private String symbol;
        @JsonProperty("slug")
        private String slug;
        @JsonProperty("is_active")
        private boolean isActive;
        @JsonProperty("first_historical_data")
        private Date firstHistoricalData;
        @JsonProperty("last_historical_data")
        private Date lastHistoricalData;
        @JsonProperty("platform")
        private Platform platform;
    }

    @Data
    public static class Platform {
        @JsonProperty("id")
        private int id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("symbol")
        private String symbol;
        @JsonProperty("slug")
        private String slug;
        @JsonProperty("token_address")
        private String tokenAddress;
    }
}