package com.jackson.microservice_kafka.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProducerDto {

    private String orderNumber;
    private String status;
    private String message;

}
