package com.chs.property_service.repository;

import com.chs.property_service.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    Optional<Building> findByNameContainingIgnoreCase(@Param("name") String name);

    Optional<Building> findById(@Param("id") long id);

    @Query(
            nativeQuery = true,
            value = """
        SELECT
            b.id,
            b.name,
            COUNT(t.id)
        FROM building b
        LEFT JOIN tenant t
            ON t.building_id = b.id
        GROUP BY b.id, b.name
        """
    )
    List<Object[]> getBuildingSummary();
}
