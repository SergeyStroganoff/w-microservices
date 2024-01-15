package com.stroganov.service;

import com.stroganov.domain.model.warehouse.Warehouse;
import com.stroganov.exception.MicroserviceCommunicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component("webservice-client")
public class WarehouseServiceWebClientImpl implements WarehouseService {

    private final Logger logger = LoggerFactory.getLogger(WarehouseServiceWebClientImpl.class);

    @Override
    public boolean warehouseExist(int id, String jwtToken) throws MicroserviceCommunicationException {
        Warehouse warehouse = WebClient.create(WarehouseService.REST_API_ADDRESS + id)
                .get()
                .header("Authorization", jwtToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Warehouse.class).block();
        return warehouse != null;
    }
}
