package com.stroganov.service;


import com.stroganov.exception.MicroserviceCommunicationException;

public interface WarehouseService {

    String REST_API_ADDRESS = "http://localhost:8765/warehouse-service/api/warehouses/";

    boolean warehouseExist(int id, String jwtToken) throws MicroserviceCommunicationException;
}
