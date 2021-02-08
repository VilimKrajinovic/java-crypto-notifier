package com.crypto.org.cryptonotifier.api.models.quotes;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class QuoteCryptoInfo {
    @JsonProperty("data")
    public Data data;
    @JsonProperty("status")
    public Status status;

    @lombok.Data
    public static class Status {
        @JsonProperty("timestamp")
        public String timestamp;
        @JsonProperty("error_code")
        public Integer errorCode;
        @JsonProperty("error_message")
        public String errorMessage;
        @JsonProperty("elapsed")
        public Integer elapsed;
        @JsonProperty("credit_count")
        public Integer creditCount;

    }

    @lombok.Data
    public static class Data {
        public Map<String, Object> cryptoCoinMap = new LinkedHashMap<>();

        @JsonAnySetter
        void setCryptoCoinMap(String key, Object value) {
            cryptoCoinMap.put(key, value);
        }

        @lombok.Data
        public static class CryptoCoin {
            @JsonProperty("id")
            public Integer id;
            @JsonProperty("name")
            public String name;
            @JsonProperty("symbol")
            public String symbol;
            @JsonProperty("slug")
            public String slug;
            @JsonProperty("is_active")
            public Integer isActive;
            @JsonProperty("is_fiat")
            public Integer isFiat;
            @JsonProperty("circulating_supply")
            public Integer circulatingSupply;
            @JsonProperty("total_supply")
            public Integer totalSupply;
            @JsonProperty("max_supply")
            public Integer maxSupply;
            @JsonProperty("date_added")
            public String dateAdded;
            @JsonProperty("num_market_pairs")
            public Integer numMarketPairs;
            @JsonProperty("cmc_rank")
            public Integer cmcRank;
            @JsonProperty("last_updated")
            public String lastUpdated;
            @JsonProperty("tags")
            public List<String> tags = null;
            @JsonProperty("platform")
            public Object platform;
            @JsonProperty("quote")
            public Quote quote;

            @lombok.Data
            public static class Quote {
                public Map<String, Currency> currencyMap;

                @lombok.Data
                public static class Currency {
                    @JsonProperty("price")
                    public Double price;
                    @JsonProperty("volume_24h")
                    public Double volume24h;
                    @JsonProperty("percent_change_1h")
                    public Double percentChange1h;
                    @JsonProperty("percent_change_24h")
                    public Double percentChange24h;
                    @JsonProperty("percent_change_7d")
                    public Double percentChange7d;
                    @JsonProperty("percent_change_30d")
                    public Double percentChange30d;
                    @JsonProperty("market_cap")
                    public Double marketCap;
                    @JsonProperty("last_updated")
                    public String lastUpdated;
                }
            }
        }
    }
}
