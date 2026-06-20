package com.chs.ticket_service.client;

import com.chs.ticket_service.dto.response.APIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "property-service",
        url = "property-service:8080"
)
public interface PropertyClient {

    @GetMapping("/tenant/{id}")
    APIResponse getTenant(@PathVariable("id") Long id);

    @GetMapping("/building/{id}")
    APIResponse getBuilding(@PathVariable("id") Long id);
}
