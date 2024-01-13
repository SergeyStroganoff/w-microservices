package com.stroganov.service;


import com.stroganov.domain.model.warehouse.Warehouse;
import com.stroganov.exception.MicroserviceCommunicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component("rest-service")
public class WarehouseServiceRestTemplateImpl implements WarehouseService {

    private final Logger logger = LoggerFactory.getLogger(WarehouseServiceRestTemplateImpl.class);

    @Override
    public boolean warehouseExist(int id, String jwtToken) throws MicroserviceCommunicationException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", jwtToken);
        HttpEntity<Integer> request = new HttpEntity<>(id,headers);
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(WarehouseService.REST_API_ADDRESS + id, HttpMethod.GET, request, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return true;
            }
            // Warehouse warehouse = restTemplate.getForObject(WarehouseService.REST_API_ADDRESS + id, Warehouse.class);
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new MicroserviceCommunicationException(e.getMessage(), e);
        }
        return Boolean.FALSE;
    }
}
