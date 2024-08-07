package com.stock.exchange.mapper;


import com.stock.exchange.controller.request.StockRequest;
import com.stock.exchange.controller.response.StockResponse;
import com.stock.exchange.entity.StockEntity;
import com.stock.exchange.model.StockModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class StockMapper {
    public abstract StockModel mapRequestToModel(StockRequest stockRequest);

    public abstract StockEntity mapModelToEntity(StockModel stockModel);

    public abstract StockModel mapEntityToModel(StockEntity stockEntity);

    public abstract StockResponse mapModelToResponse(StockModel stockModel);

}
