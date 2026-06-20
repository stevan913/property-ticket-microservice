package com.chs.ticket_service.dto.response.ticket;

import com.chs.ticket_service.entity.Ticket;
import lombok.Data;

@Data
public class ResponseTicketDTO {
    private long id;
    private String title;
    private String description;
    private String status;
    private Long tenantId;
    private Long buildingId;

    public ResponseTicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.title = ticket.getTitle();
        this.description = ticket.getDescription();
        this.status = ticket.getStatus().toString();
        this.tenantId = ticket.getTenantId();
        this.buildingId = ticket.getBuildingId();
    }
}
