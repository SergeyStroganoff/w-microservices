package com.stroganov.service;

import com.stroganov.domain.model.warehouse.Warehouse;
import com.stroganov.exception.MicroserviceCommunicationException;
import com.stroganov.service.feign.WarehouseFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("feign-client")
public class WarehouseServiceFeign implements WarehouseService {

    private final WarehouseFeignClient warehouseFeignClient;

    @Autowired

    public WarehouseServiceFeign(WarehouseFeignClient  warehouseFeignClient) {
        this.warehouseFeignClient = warehouseFeignClient;
    }

    @Override
    public boolean warehouseExist(int id, String jwtToken) throws MicroserviceCommunicationException {
        Warehouse warehouse = warehouseFeignClient.getWarehouseById(id);
        return warehouse != null;
    }
}
