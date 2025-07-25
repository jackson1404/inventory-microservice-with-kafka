package com.jackson.microservice_kafka.inventory_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Value("${app.topics.order-created}")
    private String orderCreatedTopic;

    @Value("${app.kafka.consumer-groups.inventory-order-created}")
    private String inventoryOrderCreatedGroup;

    @KafkaListener(topics = "#{__listener.orderCreatedTopic}", groupId = "#{__listener.inventoryOrderCreatedGroup}")
    public void consume(String messageJson) throws JsonProcessingException {
        System.out.println("Received raw message: " + messageJson);

        ObjectMapper mapper = new ObjectMapper();
        OrderConsumeDto event = mapper.readValue(messageJson, OrderConsumeDto.class);

        System.out.println("Order Service got the order: " + event.getOrderNumber());
    }

    @KafkaListener(topics = "#{__listener.inventoryCheckTopic}", groupId = "#{__listener.inventoryCheckGroup}")
    public void consumeInventoryCheck(String messageJson) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        OrderConsumeDto event = mapper.readValue(messageJson,OrderConsumeDto.class);

        log.info("Received inventory check for product: {}, order: {}", event.getProductId(), event.getOrderNumber());

        inventoryService.checkAndUpdateInventory(event.getProductId(), event.getOrderNumber(), event.getOrderQuantity());

    }




}
