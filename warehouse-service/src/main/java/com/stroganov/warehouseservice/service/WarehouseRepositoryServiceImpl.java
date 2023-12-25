package com.stroganov.warehouseservice.service;

import com.stroganov.warehouseservice.exception.NotFoundException;
import com.stroganov.warehouseservice.model.warehouse.Warehouse;
import com.stroganov.warehouseservice.reopository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class WarehouseRepositoryServiceImpl implements WarehouseRepositoryService {
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseRepositoryServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public Warehouse getWarehouseByID(int id) throws NotFoundException {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("warehouse with id: %s not found", id)));
    }

    @Override
    public Warehouse getWarehouseByName(String warehouseName) throws NotFoundException {
        return warehouseRepository.findByWarehouseName(warehouseName)
                .orElseThrow(() -> new NotFoundException(format("warehouse with name: %s not found", warehouseName)));
    }
}
