/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.microservice_kafka.inventory_service.schedular;

import com.jackson.microservice_kafka.inventory_service.model.ProductEntity;
import com.jackson.microservice_kafka.inventory_service.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Scheduled(fixedRate = 10000)
    public void checkStockLevel(){

        List<ProductEntity> productEntities = productRepository.findLowStockItems();

    }



}
