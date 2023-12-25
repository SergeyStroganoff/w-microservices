package com.stroganov.service;


import com.stroganov.domain.model.warehouse.Warehouse;
import com.stroganov.exception.MicroserviceCommunicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("rest-service")
public class WarehouseServiceRestTemplateImpl implements WarehouseService {

    private final Logger logger = LoggerFactory.getLogger(WarehouseServiceRestTemplateImpl.class);


    @Override
    public boolean WarehouseExist(int id) throws MicroserviceCommunicationException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Integer> request = new HttpEntity<>(id);
        ResponseEntity<Warehouse> responseEntity ;
        try {
            responseEntity = restTemplate.exchange(WarehouseService.REST_API_ADDRESS + id, HttpMethod.GET, request, Warehouse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return true;
            }
           // Warehouse warehouse = restTemplate.getForObject(WarehouseService.REST_API_ADDRESS + id, Warehouse.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new MicroserviceCommunicationException(e.getMessage());
        }
        return Boolean.FALSE;
    }
}
