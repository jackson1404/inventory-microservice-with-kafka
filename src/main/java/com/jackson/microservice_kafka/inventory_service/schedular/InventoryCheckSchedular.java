/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.microservice_kafka.inventory_service.schedular;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackson.microservice_kafka.inventory_service.dto.ProductStockDto;
import com.jackson.microservice_kafka.inventory_service.model.ProductEntity;
import com.jackson.microservice_kafka.inventory_service.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * InevntoryCheckSchedular Class.
 * <p>
 * </p>
 *
 * @author
 */
@Service
@Data
public class InventoryCheckSchedular {

    @Autowired
    private KafkaTemplate<String, ProductStockDto> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Scheduled(fixedRate = 10000)
    public void checkStockLevel(){

        List<ProductEntity> productEntities = productRepository.findLowStockItems();
        for (ProductEntity item : productEntities) {
            ProductStockDto event = new ProductStockDto(
                    item.getProductId(), item.getProductName(), "OUT_OF_STOCK", LocalDate.now().toString()
            );
            System.out.println("before sent");

            kafkaTemplate.send("stock-status", event.getProductName(), event);

            System.out.println("after sent");
        }

    }



}
