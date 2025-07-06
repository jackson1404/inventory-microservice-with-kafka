package com.jackson.microservice_kafka.inventory_service.service;

import jakarta.transaction.Transactional;

public interface InventoryService {

    void checkAndUpdateInventory(Long productId, String orderNumber, int quantity);

}
