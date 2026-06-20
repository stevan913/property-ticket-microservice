package com.chs.property_service.controller;

import com.chs.property_service.dto.request.tenant.RequestTenantDTO;
import com.chs.property_service.dto.response.APIResponse;
import com.chs.property_service.dto.response.tenant.ResponseTenantDTO;
import com.chs.property_service.exception.DuplicateEntityException;
import com.chs.property_service.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenant")
public class TenantController {

    private final TenantService tenantService;
    private static final String ID = "/{id}";
    private static final String UPDATE = "/update";
    private static final String SET_BUILDING = "/set-building";
    private static final String TENANT_ID = "/{tenant_id}";
    private static final String BUILDING_ID = "/{building_id}";

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> addTenant(@RequestBody RequestTenantDTO requestTenantDTO) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            ResponseTenantDTO resp = tenantService.save(requestTenantDTO);
            status = HttpStatus.CREATED;
            response.setSuccess(resp);
            response.setMessage("Tenant Created Successfully");
        } catch (DuplicateEntityException e) {
            status = HttpStatus.CONFLICT;
            response.setFail(e.getMessage());
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(value = ID)
    public ResponseEntity<APIResponse> getTenantById(@PathVariable("id") long id) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            ResponseTenantDTO resp = tenantService.findById(id);
            status = HttpStatus.OK;
            response.setSuccess(resp);
            response.setMessage("Tenant Found Successfully");
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(value = ID)
    public ResponseEntity<APIResponse> updateTenant(@PathVariable("id") long tenantId,
                                                    @RequestBody RequestTenantDTO requestTenantDTO) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            ResponseTenantDTO resp = tenantService.update(tenantId, requestTenantDTO);
            status = HttpStatus.OK;
            response.setSuccess(resp);
            response.setMessage("Tenant Update Successfully");
        } catch (DuplicateEntityException e) {
            status = HttpStatus.CONFLICT;
            response.setFail(e.getMessage());
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<APIResponse> deleteTenant(@PathVariable("id") long tenantId) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            tenantService.delete(tenantId);
            status = HttpStatus.OK;
            response.setSuccess(null);
            response.setMessage("Tenant Delete Successfully");
        } catch (DuplicateEntityException e) {
            status = HttpStatus.CONFLICT;
            response.setFail(e.getMessage());
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(value = SET_BUILDING)
    public ResponseEntity<APIResponse> setTenantToBuilding(@RequestParam("tenant_id") long tenantId,
                                                           @RequestParam("building_id") long buildingId) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            ResponseTenantDTO resp = tenantService.setTenantToBuilding(tenantId, buildingId);
            status = HttpStatus.CREATED;
            response.setSuccess(resp);
            response.setMessage("Tenant has been set to Building Successfully");
        } catch (DuplicateEntityException e) {
            status = HttpStatus.CONFLICT;
            response.setFail(e.getMessage());
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
            e.printStackTrace();
        }

        return ResponseEntity.status(status).body(response);
    }

    @GetMapping
    public List<ResponseTenantDTO> findAll() {
        return tenantService.findAll();
    }
}
