package com.chs.property_service.service;

import com.chs.property_service.dto.request.building.RequestBuildingDTO;
import com.chs.property_service.dto.response.building.BuildingSummaryDTO;
import com.chs.property_service.dto.response.building.ResponseBuildingDTO;
import com.chs.property_service.entity.Building;
import com.chs.property_service.exception.DuplicateEntityException;
import com.chs.property_service.repository.BuildingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {
    private final BuildingRepository repository;

    public BuildingService(BuildingRepository repository) {
        this.repository = repository;
    }

    public ResponseBuildingDTO save(RequestBuildingDTO request) {
        if (repository.findByNameContainingIgnoreCase(request.getName()).isPresent()) {
            throw new DuplicateEntityException(
                    "Building with name " + request.getName() + " already exists");
        }

        Building building = new Building();
        building.setName(request.getName());

        repository.save(building);

        return new ResponseBuildingDTO(building);
    }

    public ResponseBuildingDTO update(long buildingId, RequestBuildingDTO request) {
        Optional<Building> optBuilding = repository.findById(buildingId);

        if(optBuilding.isPresent()) {
            Building building = optBuilding.get();
            if (repository.findByNameContainingIgnoreCase(request.getName()).isPresent()) {
                throw new DuplicateEntityException(
                        "Building with name " + request.getName() + " already exists");
            }

            building.setName(request.getName());

            repository.save(building);
            return new ResponseBuildingDTO(building);
        } else
            throw new EntityNotFoundException("Building with id " + buildingId + " not found");
    }

    public void delete(long buildingId) {
        repository.deleteById(buildingId);
    }

    public ResponseBuildingDTO findById(long id) {
        Building building = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Building with id " + id + " not found")
        );
        return new ResponseBuildingDTO(building);
    }

    public List<ResponseBuildingDTO> findAll() {
//        List<ResponseBuildingDTO> list = new ArrayList<>();
//        List<Building> buildings = repository.findAll();
//        for (Building building : buildings) {
//            list.add(new ResponseBuildingDTO(building));
//        }
//        return list;

        return repository.findAll().stream().map(ResponseBuildingDTO::new).toList();
    }

    public Building getById(long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Building with id " + id + " not found")
        );
    }

    public List<BuildingSummaryDTO> getBuildingSummary() {

        return repository.getBuildingSummary()
                .stream()
                .map(row -> new BuildingSummaryDTO(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).longValue()
                ))
                .toList();
    }
}