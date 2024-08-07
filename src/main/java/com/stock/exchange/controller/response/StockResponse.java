package com.stock.exchange.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal currentPrice;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;

}
