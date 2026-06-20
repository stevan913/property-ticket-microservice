package com.chs.property_service.dto.response.building;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuildingSummaryDTO {
    private Long buildingId;
    private String buildingName;
    private Long totalTenant;
}
