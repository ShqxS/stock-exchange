package com.stock.exchange.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Positive
    private BigDecimal currentPrice;

}
