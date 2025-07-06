package com.jackson.microservice_kafka.inventory_service.consumer;

import com.jackson.microservice_kafka.inventory_service.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
@Slf4j
public class InventoryConsumer {

    @Autowired
    private InventoryService inventoryService;

    @Value("${app.topics.inventory-check}")
    private String inventoryCheckTopic;

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    @KafkaListener(topics = "#{__listener.inventoryCheckTopic}", groupId = "#{__listener.consumerGroupId}")
    public void consumeInventoryCheck(ConsumerRecord<Long, Map<String, Object>> record){
        Map<String, Object> value = record.value();
        Long productId = record.key();
        String orderNumber = (String) value.get("orderNumber");
        int quantity = (Integer) value.get("quantity");

        log.info("Received inventory check for product: {}, order: {}", productId, orderNumber);

        inventoryService.checkAndUpdateInventory(productId, orderNumber, quantity);

    }




}
