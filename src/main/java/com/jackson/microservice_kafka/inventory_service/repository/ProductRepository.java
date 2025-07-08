package com.jackson.microservice_kafka.inventory_service.repository;

import com.jackson.microservice_kafka.inventory_service.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByProductId(Long productId);

    @Query("SELECT p FROM ProductEntity p WHERE p.productQuantity <10")
    List<ProductEntity> findLowStockItems();
}
