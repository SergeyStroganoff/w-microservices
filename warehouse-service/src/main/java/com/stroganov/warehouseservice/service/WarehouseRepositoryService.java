package com.stroganov.warehouseservice.service;

import com.stroganov.warehouseservice.exception.NotFoundException;
import com.stroganov.warehouseservice.model.warehouse.Warehouse;
import org.springframework.stereotype.Service;

@Service
public interface WarehouseRepositoryService {
    Warehouse getWarehouseByID(int id) throws NotFoundException;

    Warehouse getWarehouseByName(String warehouseName) throws NotFoundException;
}
