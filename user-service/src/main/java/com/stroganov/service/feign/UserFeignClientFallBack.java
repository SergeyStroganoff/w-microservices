package com.stroganov.service.feign;

import com.stroganov.domain.model.warehouse.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientFallBack implements WarehouseFeignClient {

    //method is called when the warehouse-service is not available
    /**
     * @param id
     * @return
     */
    @Override
    public Warehouse getWarehouseById(int id) {
        return null;
    }
}
