package com.jackson.microservice_kafka.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderConsumeDto {

    private Long productId;
    private String orderNumber;
    private int orderQuantity;

}
