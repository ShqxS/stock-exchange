package com.stock.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockModel {

    private Long id;
    private String name;
    private String description;
    private BigDecimal currentPrice;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;

}
