package com.stock.exchange.repository;

import com.stock.exchange.entity.StockEntity;
import com.stock.exchange.entity.StockExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchangeEntity, Long> {
    Optional<StockExchangeEntity> findByName(String name);
}
