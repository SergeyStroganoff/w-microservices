package com.stroganov.service.feign;

import feign.Response;
import org.springframework.stereotype.Component;

@Component
public class FeignExceptionHandler implements feign.codec.ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
      switch (response.status()) {
        case 400:
          return new Exception("Feign: Bad request");
          case 404:
          if (methodKey.contains("getWarehouseById")) {
            return new Exception("Warehouse by id not found");
          }
          break;
        default:
          return new Exception("Generic feign error:" + response.status());
      }
        return null;
    }
}
