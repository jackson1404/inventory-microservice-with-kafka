package com.jackson.microservice_kafka.inventory_service.service.serviceImpl;

import com.jackson.microservice_kafka.inventory_service.dto.OrderProducerDto;
import com.jackson.microservice_kafka.inventory_service.repository.ProductRepository;
import com.jackson.microservice_kafka.inventory_service.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Data
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;

    private final KafkaTemplate<Object, Object> kafkaTemplate;

//    private final AppTopicProperties appTopicProperties;

    @Value("${app.topics.order-processed}")
    private String orderProcessedTopic;

    @Value("${app.topics.inventory-updated}")
    private String inventoryUpdatedTopic;

    @Override
    @Transactional
    public void checkAndUpdateInventory(Long productId, String orderNumber, int quantity) {

        productRepository.findByProductId(productId).ifPresent(product -> {
            if(product.getProductQuantity() >= quantity){
                product.setProductQuantity(product.getProductQuantity() - quantity);
                productRepository.save(product);

                OrderProducerDto orderProducerDto = new OrderProducerDto(
                        orderNumber,
                        "SUCCESS",
                        "Inventory updated successfully"
                );
                kafkaTemplate.send(orderProcessedTopic, orderNumber,
                        orderProducerDto);

//                kafkaTemplate.send(inventoryUpdatedTopic, productId,
//                        Map.of(
//                                "productId", productId,
//                                "quantity" , product.getProductQuantity()
//                        ));
            } else {
                kafkaTemplate.send(orderProcessedTopic, orderNumber,
                        Map.of(
                                "orderNumber", orderNumber,
                                "status", "FAILED",
                                "message", "Insufficient stock"
                        ));
            }

        });

    }
}
