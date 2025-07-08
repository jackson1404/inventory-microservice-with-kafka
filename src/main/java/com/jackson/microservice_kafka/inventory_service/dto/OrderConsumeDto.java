package com.jackson.microservice_kafka.inventory_service.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrderConsumeDto {

    private String productId;
    private String orderNumber;
    private int orderQuantity;

}
