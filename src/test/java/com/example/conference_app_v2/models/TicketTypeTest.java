package com.example.conference_app_jpa.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.example.conference_app_jpa.repositories.TicketTypeJpaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicketTypeTest {
    // @Autowired
    // private TicketTypeJpaRepository ticketTypeJpaRepository;

    // @Autowired
    // private PricingCategoryJpaRepository pricingCategoryJpaRepository;

    @Autowired
    private TicketTypeJpaRepository ticketTypeJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFind() throws Exception {
        TicketType ticket = ticketTypeJpaRepository.getOne("C");
        assertNotNull(ticket);
    }

    @Test
    public void testTrue() throws Exception {
        List<TicketType> tickets = ticketTypeJpaRepository.findByIncludesWorkshopTrue();
        assertTrue(tickets.size() > 0);
    }

    @Test
    @Transactional
    public void testSaveAndGetAndDelete() throws Exception {
        TicketType ticketType = new TicketType();
        ticketType.setDescription("Any one that wishes to come");
        ticketType.setIncludesWorkshop(false);
        ticketType.setTicketTypeName("General Public");
        ticketType.setTicketTypeCode("G");

        ticketType = ticketTypeJpaRepository.saveAndFlush(ticketType);

        // clear the persistence context so we don't return the previously cached
        // location object
        // this is a test only thing and normally doesn't need to be done in prod code
        entityManager.clear();

        TicketType savedTicketType = ticketTypeJpaRepository.getOne(ticketType.getTicketTypeCode());
        assertEquals("General Public", savedTicketType.getTicketTypeName());

        ticketTypeJpaRepository.deleteById(savedTicketType.getTicketTypeCode());
    }
}
