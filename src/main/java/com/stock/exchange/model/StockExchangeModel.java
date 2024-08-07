package com.stock.exchange.model;

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
public class StockExchangeModel {

    private Long id;
    private String name;
    private String description;
    private boolean liveInMarket;
    private Set<StockModel> stocks;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;

}
