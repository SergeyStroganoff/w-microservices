package com.stroganov.warehouseservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Warehouse controller", description = "Warehouse management APIs")
@RestController
@RequestMapping("/api/warehouses")
@RefreshScope //
@Validated
public class WarehouseController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the WAREHOUSE API";
    }
}
