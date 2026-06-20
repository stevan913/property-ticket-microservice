package com.chs.ticket_service.dto.request.ticket;

import com.chs.ticket_service.enum_package.TicketStatus;
import lombok.Data;

@Data
public class RequestTicketDTO {
    private String title;
    private String description;
    private TicketStatus status;
    private Long tenantId;
    private Long buildingId;
}
