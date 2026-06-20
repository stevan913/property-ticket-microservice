package com.chs.ticket_service.repository;

import com.chs.ticket_service.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Optional<Ticket> findById(@Param("id") Long id);

    Optional<Ticket> findByTitleContainingIgnoreCase(@Param("title") String title);
}
