/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.microservice_kafka.inventory_service.schedular;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    @Scheduled(fixedRate = 10000)
    public void checkStockLevel(){

    }



}
