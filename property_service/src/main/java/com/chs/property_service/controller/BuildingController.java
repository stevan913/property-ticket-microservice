package com.chs.property_service.controller;

import com.chs.property_service.dto.request.building.RequestBuildingDTO;
import com.chs.property_service.dto.response.APIResponse;
import com.chs.property_service.dto.response.building.BuildingSummaryDTO;
import com.chs.property_service.dto.response.building.ResponseBuildingDTO;
import com.chs.property_service.exception.DuplicateEntityException;
import com.chs.property_service.service.BuildingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/building")
public class BuildingController {

    private final BuildingService buildingService;
    private static final String ID = "/{id}";
    private static final String SUMMARY = "/summary";

    public BuildingController(BuildingService buildingService) {this.buildingService = buildingService;}

    @PostMapping
    public ResponseEntity<APIResponse> addBuilding(@RequestBody RequestBuildingDTO requestBuildingDTO) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            ResponseBuildingDTO resp = buildingService.save(requestBuildingDTO);
            status = HttpStatus.CREATED;
            response.setSuccess(resp);
            response.setMessage("Building Created Successfully");
        } catch (DuplicateEntityException e) {
            status = HttpStatus.CONFLICT;
            response.setFail(e.getMessage());
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(value = ID)
    public ResponseEntity<APIResponse> updateBuilding(@PathVariable("id") long buildingId,
                                                      @RequestBody RequestBuildingDTO requestBuildingDTO) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            ResponseBuildingDTO resp = buildingService.update(buildingId, requestBuildingDTO);
            status = HttpStatus.OK;
            response.setSuccess(resp);
            response.setMessage("Building Update Successfully");
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
    public ResponseEntity<APIResponse> deleteBuilding(@PathVariable("id") long buildingId) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            buildingService.delete(buildingId);
            status = HttpStatus.OK;
            response.setSuccess(null);
            response.setMessage("Building Delete Successfully");
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
    public ResponseEntity<APIResponse> getBuildingById(@PathVariable("id") long id) {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            ResponseBuildingDTO resp = buildingService.findById(id);
            status = HttpStatus.OK;
            response.setSuccess(resp);
            response.setMessage("Building Found Successfully");
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @GetMapping
    public ResponseEntity<APIResponse> findAll() {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            List<ResponseBuildingDTO> resp = buildingService.findAll();
            status = HttpStatus.OK;
            response.setSuccess(resp);
            response.setMessage("Building Get All");
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(SUMMARY)
    public ResponseEntity<APIResponse> summary() {
        APIResponse response = new APIResponse();
        HttpStatus status;

        try {
            List<BuildingSummaryDTO> resp = buildingService.getBuildingSummary();
            status = HttpStatus.OK;
            response.setSuccess(resp);
            response.setMessage("Building Summary Get");
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            response.setFail(e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }
}
