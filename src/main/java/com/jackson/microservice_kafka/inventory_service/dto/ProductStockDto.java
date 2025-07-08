/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.microservice_kafka.inventory_service.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * ProductStockDto Class.
 * <p>
 * </p>
 *
 * @author
 */

@Data
@RequiredArgsConstructor
public class ProductStockDto {

    private String productId;
    private String productName;
    private int productQuantity;

}
