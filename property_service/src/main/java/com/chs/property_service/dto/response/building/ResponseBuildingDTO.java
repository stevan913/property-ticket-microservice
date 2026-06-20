package com.chs.property_service.dto.response.building;

import com.chs.property_service.entity.Building;
import lombok.Data;

@Data
public class ResponseBuildingDTO {
    private long id;
    private String name;

    public ResponseBuildingDTO(Building building) {
        this.id = building.getId();
        this.name = building.getName();
    }
}
