package com.stock.exchange.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockExchangeResponse {

    private Long id;
    private String name;
    private String description;
    private boolean liveInMarket;
    private Set<StockResponse> stocks;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;

}
