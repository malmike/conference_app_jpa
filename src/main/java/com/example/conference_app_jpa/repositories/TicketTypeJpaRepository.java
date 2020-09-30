package com.example.conference_app_jpa.repositories;

import java.util.List;

import com.example.conference_app_jpa.models.TicketType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeJpaRepository extends JpaRepository<TicketType, String> {
    List<TicketType> findByIncludesWorkshopTrue();
}
