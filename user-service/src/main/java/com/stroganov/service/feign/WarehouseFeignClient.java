package com.stroganov.service.feign;

import com.stroganov.configuration.FeignClientConfiguration;
import com.stroganov.domain.model.warehouse.Warehouse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "warehouse-service", configuration = FeignClientConfiguration.class)
public interface WarehouseFeignClient {

    @GetMapping("/api/warehouses/{id}")
    Warehouse getWarehouseById(@PathVariable int id);
}
