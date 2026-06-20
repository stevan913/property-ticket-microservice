package com.chs.property_service.repository;

import com.chs.property_service.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findById(@Param("id") long id);

    Optional<Tenant> findByNameContainingIgnoreCase(@Param("name") String name);

    Optional<Tenant> findByEmailContainingIgnoreCase(@Param("email") String email);
}
