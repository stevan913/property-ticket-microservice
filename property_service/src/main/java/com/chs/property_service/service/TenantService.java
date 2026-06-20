package com.chs.property_service.service;

import com.chs.property_service.dto.request.tenant.RequestTenantDTO;
import com.chs.property_service.dto.response.tenant.ResponseTenantDTO;
import com.chs.property_service.entity.Tenant;
import com.chs.property_service.exception.DuplicateEntityException;
import com.chs.property_service.repository.TenantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TenantService {
    private final TenantRepository repository;
    private final BuildingService buildingService;

    public TenantService(TenantRepository repository, BuildingService buildingService) {
        this.repository = repository;
        this.buildingService = buildingService;
    }

    public ResponseTenantDTO save(RequestTenantDTO request) {
        if (repository.findByEmailContainingIgnoreCase(request.getEmail().toLowerCase()).isPresent()) {
            throw new DuplicateEntityException(
                    "Tenant with email " + request.getEmail() + " already exists");
        }

        Tenant tenant = new Tenant();
        tenant.setName(request.getName());
        tenant.setEmail(request.getEmail());

        repository.save(tenant);

        return new ResponseTenantDTO(tenant);
    }

    public ResponseTenantDTO update(long tenantId, RequestTenantDTO request) {
        Optional<Tenant> optTenant = repository.findById(tenantId);

        if (optTenant.isPresent()) {
            Tenant tenant = optTenant.get();

            Optional<Tenant> checkEmail = repository.findByEmailContainingIgnoreCase(request.getEmail().toLowerCase());

            if (checkEmail.isPresent() &&
                !checkEmail.get().getId().equals(tenantId)) {
                throw new DuplicateEntityException(
                        "Tenant with email " + request.getEmail() + " already exists");
            }

            tenant.setName(request.getName());
            tenant.setEmail(request.getEmail());

            repository.save(tenant);
            return new ResponseTenantDTO(tenant);
        } else
            throw new EntityNotFoundException("Tenant with id " + tenantId + " not found");

    }

    public void delete(long tenantId) {
        Tenant tenant = repository.findById(tenantId).orElseThrow(() -> new EntityNotFoundException("Tenant with id " + tenantId + " not found"));
        repository.delete(tenant);
    }

    public ResponseTenantDTO setTenantToBuilding(long tenantId, long buildingId) {
        Optional<Tenant> optTenant = repository.findById(tenantId);

        if (optTenant.isPresent()) {
            Tenant tenant = optTenant.get();
            if (Objects.isNull(tenant.getBuilding()) || !tenant.getBuilding().getId().equals(buildingId) ) {
                tenant.setBuilding(buildingService.getById(buildingId));
                repository.save(tenant);
            }
            return new ResponseTenantDTO(tenant);
        } else
            throw new EntityNotFoundException("Tenant with id " + tenantId + " not found");
    }

    public ResponseTenantDTO findById(long id) {
        Tenant tenant = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Tenant with id " + id + " not found")
        );
        return new ResponseTenantDTO(tenant);
    }

    public List<ResponseTenantDTO> findAll() {
//        List<ResponseTenantDTO> response = new ArrayList<>();
//        List<Tenant> tenants = repository.findAll();
//        for (Tenant tenant : tenants) {
//            response.add(new ResponseTenantDTO(tenant));
//        }
//        return response;
        return repository.findAll().stream().map(ResponseTenantDTO::new).toList();
    }
}
