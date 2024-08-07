package com.stock.exchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock_exchanges")
public class StockExchangeEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String description;
    private boolean liveInMarket;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "stock_exchange_stocks",
            joinColumns = @JoinColumn(name = "stock_exchange_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id")
    )
    private Set<StockEntity> stocks = new HashSet<>();

    public void updateLiveInMarketStatus() {
        this.liveInMarket = this.stocks.size() >= 5;
    }

    public void addStock(StockEntity stock) {
        this.stocks.add(stock);
        stock.getStockExchanges().add(this);
    }

    public void removeStock(StockEntity stock) {
        this.stocks.remove(stock);
        stock.getStockExchanges().remove(this);
    }


}
