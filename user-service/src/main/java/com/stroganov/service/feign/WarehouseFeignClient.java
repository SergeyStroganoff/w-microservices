package com.stroganov.service.feign;

import com.stroganov.configuration.FeignClientConfiguration;
import com.stroganov.domain.model.warehouse.Warehouse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Primary
@FeignClient(name = "warehouse-service", configuration = FeignClientConfiguration.class, fallback = UserFeignClientFallBack.class)
public interface WarehouseFeignClient {

    @GetMapping("/api/warehouses/{id}")
    Warehouse getWarehouseById(@PathVariable int id);
}
