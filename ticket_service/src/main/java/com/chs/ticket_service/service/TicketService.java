package com.chs.ticket_service.service;

import com.chs.ticket_service.client.PropertyClient;
import com.chs.ticket_service.dto.request.ticket.RequestTicketDTO;
import com.chs.ticket_service.dto.response.APIResponse;
import com.chs.ticket_service.dto.response.ticket.ResponseTicketDTO;
import com.chs.ticket_service.entity.Ticket;
import com.chs.ticket_service.repository.TicketRepository;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository repository;
    private final PropertyClient propertyClient;
    public TicketService(TicketRepository repository, PropertyClient propertyClient) {
        this.repository = repository;
        this.propertyClient = propertyClient;
    }

    public ResponseTicketDTO create(RequestTicketDTO requestTicketDTO) {
        Ticket ticket = new Ticket();

        // Validate the Tenant and Building Data
        this.getTenantId(requestTicketDTO.getTenantId());
        this.getBuildingId(requestTicketDTO.getBuildingId());

        ticket.setTitle(requestTicketDTO.getTitle());
        ticket.setDescription(requestTicketDTO.getDescription());
        ticket.setTenantId(requestTicketDTO.getTenantId());
        ticket.setBuildingId(requestTicketDTO.getBuildingId());
        ticket.setStatus(requestTicketDTO.getStatus());

        return new ResponseTicketDTO(repository.save(ticket));
    }

    public ResponseTicketDTO update(long id, RequestTicketDTO requestTicketDTO) {
        Optional<Ticket> optTicket = repository.findById(id);
        if (optTicket.isPresent()) {
            Ticket ticket = optTicket.get();

            // Validate the Tenant and Building Data
            this.getTenantId(requestTicketDTO.getTenantId());
            this.getBuildingId(requestTicketDTO.getBuildingId());

            ticket.setTitle(requestTicketDTO.getTitle());
            ticket.setDescription(requestTicketDTO.getDescription());
            ticket.setTenantId(requestTicketDTO.getTenantId());
            ticket.setBuildingId(requestTicketDTO.getBuildingId());
            ticket.setStatus(requestTicketDTO.getStatus());

            return new ResponseTicketDTO(repository.save(ticket));
        } else throw new EntityNotFoundException("Ticket not found");
    }

    public void delete(long id) {
        Ticket ticket = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        repository.delete(ticket);
    }

    public ResponseTicketDTO findById(Long id) {
        Optional<Ticket> optTicket = repository.findById(id);
        if (optTicket.isPresent()) {
            return new ResponseTicketDTO(optTicket.get());
        } else throw new EntityNotFoundException("Ticket not found");
    }

    public List<ResponseTicketDTO> findAll() {
//        List<ResponseTicketDTO> list = new ArrayList<>();
//        for  (Ticket ticket : repository.findAll()) {
//            list.add(new ResponseTicketDTO(ticket));
//        }
//        return list;

        return repository.findAll()
                .stream()
                .map(ResponseTicketDTO::new)
                .toList();
    }

    private void getTenantId(long tenantId) {
        try {
            APIResponse response = propertyClient.getTenant(tenantId);

            System.out.println(response);

        } catch (FeignException e) {
            e.printStackTrace();

            throw new EntityNotFoundException(
                    "Tenant with id " + tenantId + " not found");
        }
    }

    private void getBuildingId(long buildingId) {
        try {
            propertyClient.getBuilding(buildingId);
        } catch (FeignException e) {
            throw new EntityNotFoundException(
                    "Building with id "+buildingId+" not found");
        }
    }
}
