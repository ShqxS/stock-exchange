package com.stock.exchange.mapper;


import com.stock.exchange.controller.request.StockExchangeRequest;
import com.stock.exchange.controller.response.StockExchangeResponse;
import com.stock.exchange.entity.StockExchangeEntity;
import com.stock.exchange.model.StockExchangeModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class StockExchangeMapper {

    public abstract StockExchangeModel mapEntityToModel(StockExchangeEntity stockExchangeEntity);

    public abstract StockExchangeResponse mapModelToResponse(StockExchangeModel stockExchangeModel);

    public abstract StockExchangeModel mapRequestToModel(StockExchangeRequest stockExchangeRequest);

    public abstract StockExchangeEntity mapModelToEntity(StockExchangeModel stockExchangeModel);
}
