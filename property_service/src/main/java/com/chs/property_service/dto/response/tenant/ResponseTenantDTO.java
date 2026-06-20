package com.chs.property_service.dto.response.tenant;

import com.chs.property_service.entity.Tenant;
import lombok.Data;

@Data
public class ResponseTenantDTO {
    private long id;
    private String name, buildingName;
    private String email;
    private long buildingId;

    public ResponseTenantDTO(Tenant tenant) {
        this.id = tenant.getId();
        this.name = tenant.getName();
        this.email = tenant.getEmail();
        this.buildingId = tenant.getBuilding() == null ? 0L : tenant.getBuilding().getId();
        this.buildingName = tenant.getBuilding() == null ? "---" : tenant.getBuilding().getName();
    }
}
