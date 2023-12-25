package com.stroganov.warehouseservice.reopository;

import com.stroganov.warehouseservice.model.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    @Query("select w from Warehouse w where w.warehouseName = ?1")
    Optional<Warehouse> findByWarehouseName(String warehouseName);
}
