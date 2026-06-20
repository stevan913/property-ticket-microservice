package com.chs.ticket_service.controller;

import com.chs.ticket_service.dto.request.ticket.RequestTicketDTO;
import com.chs.ticket_service.dto.response.APIResponse;
import com.chs.ticket_service.dto.response.ticket.ResponseTicketDTO;
import com.chs.ticket_service.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;
    public TicketController(TicketService ticketService) {this.ticketService = ticketService;}

    private final static String ID = "/{id}";

    @PostMapping
    public ResponseEntity<APIResponse> createTicket(@RequestBody RequestTicketDTO request) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            ResponseTicketDTO resp = ticketService.create(request);
            status = HttpStatus.CREATED;
            response.setSuccess(resp);
            response.setMessage("Ticket created");
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(value = ID)
    public ResponseEntity<APIResponse> updateTicket(@PathVariable("id") long id,
                                                    @RequestBody RequestTicketDTO request) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            ResponseTicketDTO resp = ticketService.update(id, request);
            status = HttpStatus.OK;
            response.setSuccess(resp);
            response.setMessage("Ticket updated");
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(value = ID)
    public ResponseEntity<APIResponse> getTicketById(@PathVariable("id") long id) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            ResponseTicketDTO resp = ticketService.findById(id);
            status = HttpStatus.OK;
            response.setSuccess(resp);
            response.setMessage("Ticket Get");
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<APIResponse> deleteTicket(@PathVariable("id") long id) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            ticketService.delete(id);
            status = HttpStatus.OK;
            response.setSuccess(null);
            response.setMessage("Delete Ticket Success");
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getTicketAll() {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            List<ResponseTicketDTO> resp = ticketService.findAll();
            status = HttpStatus.OK;
            response.setSuccess(resp);
            response.setMessage("Ticket Get");
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }
}
