package com.example.conference_app_jpa.models;

import com.example.conference_app_jpa.repositories.PricingCategoryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PricingCategoryTest {
    @Autowired
    private PricingCategoryJpaRepository pricingCategoryJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFind() throws Exception {
        PricingCategory pricingCategory = pricingCategoryJpaRepository.getOne("L");
        assertNotNull(pricingCategory);
    }

    @Test
    @Transactional
    public void testSaveAndGetAndDelete() throws Exception {
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        // Add 2 days to start date
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date endDate = calendar.getTime();

        PricingCategory pricingCategory = new PricingCategory();
        pricingCategory.setPricingCategoryCode("J");
        pricingCategory.setPricingCategoryName("Acient Adopters");
        pricingCategory.setPricingStartDate(startDate);
        pricingCategory.setPricingEndDate(endDate);

        pricingCategory = pricingCategoryJpaRepository.saveAndFlush(pricingCategory);

        // clear the persistence context so we don't return the previously cached
        // location object
        // this is a test only thing and normally doesn't need to be done in prod code
        entityManager.clear();

        PricingCategory otherPricingCategory = pricingCategoryJpaRepository
                .getOne(pricingCategory.getPricingCategoryCode());
        assertEquals("Acient Adopters", otherPricingCategory.getPricingCategoryName());

        pricingCategoryJpaRepository.deleteById(otherPricingCategory.getPricingCategoryCode());
    }

}
