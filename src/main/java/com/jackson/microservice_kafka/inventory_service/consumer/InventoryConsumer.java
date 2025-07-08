package com.jackson.microservice_kafka.inventory_service.consumer;

import com.jackson.microservice_kafka.inventory_service.dto.OrderConsumeDto;
import com.jackson.microservice_kafka.inventory_service.service.InventoryService;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
@Slf4j
@Data
public class InventoryConsumer {

    @Autowired
    private InventoryService inventoryService;

    @Value("${app.topics.inventory-check}")
    private String inventoryCheckTopic;

    @Value("${app.kafka.consumer-groups.inventory-check}")
    private String inventoryCheckGroup;

    @KafkaListener(topics = "#{__listener.inventoryCheckTopic}", groupId = "#{__listener.inventoryCheckGroup}")
    public void consumeInventoryCheck(ConsumerRecord<Long, OrderConsumeDto> record){

        OrderConsumeDto orderConsumeDto = record.value();
        Long productId = orderConsumeDto.getProductId();
        String orderNumber = orderConsumeDto.getOrderNumber();
        int quantity = orderConsumeDto.getOrderQuantity();

        log.info("Received inventory check for product: {}, order: {}", productId, orderNumber);

        inventoryService.checkAndUpdateInventory(productId, orderNumber, quantity);

    }




}
