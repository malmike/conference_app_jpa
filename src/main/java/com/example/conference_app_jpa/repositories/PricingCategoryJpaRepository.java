package com.example.conference_app_jpa.repositories;

import com.example.conference_app_jpa.models.PricingCategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingCategoryJpaRepository extends JpaRepository<PricingCategory, String> {

}
