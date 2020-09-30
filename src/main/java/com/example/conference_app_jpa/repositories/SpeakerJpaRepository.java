package com.example.conference_app_jpa.repositories;

import java.util.List;

import com.example.conference_app_jpa.models.Speaker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerJpaRepository extends JpaRepository<Speaker, Long> {
    List<Speaker> findByFirstNameAndLastName(String firstName, String lastName);

    List<Speaker> findByLastNameOrderByFirstNameAsc(String lastName);

    List<Speaker> findByFirstNameOrLastName(String firstName, String lastName);

    List<Speaker> findBySpeakerPhotoNull();

    List<Speaker> findByCompanyIn(List<String> companies);

    List<Speaker> findByCompanyIgnoreCase(String company);
}
