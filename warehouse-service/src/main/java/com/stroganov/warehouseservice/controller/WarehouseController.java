package com.stroganov.warehouseservice.controller;

import com.stroganov.warehouseservice.exception.NotFoundException;
import com.stroganov.warehouseservice.model.warehouse.Warehouse;
import com.stroganov.warehouseservice.service.WarehouseRepositoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Warehouse controller", description = "Warehouse management APIs")
@RestController
@RequestMapping("/api/warehouses")
@RefreshScope //
@Validated
public class WarehouseController {
    private final WarehouseRepositoryService warehouseRepositoryService;


    @Autowired
    public WarehouseController(WarehouseRepositoryService warehouseRepositoryService) {
        this.warehouseRepositoryService = warehouseRepositoryService;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the WAREHOUSE API";
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouseById(@PathVariable int id) throws NotFoundException {
        return warehouseRepositoryService.getWarehouseByID(id);
    }

    @GetMapping("/{name}/name")
    public Warehouse getWarehouseByName(@PathVariable String name) throws NotFoundException {
        return warehouseRepositoryService.getWarehouseByName(name);
    }
}
