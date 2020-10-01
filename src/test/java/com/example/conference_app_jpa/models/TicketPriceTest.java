package com.example.conference_app_jpa.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.example.conference_app_jpa.repositories.PricingCategoryJpaRepository;
import com.example.conference_app_jpa.repositories.TicketPriceJpaRepository;
import com.example.conference_app_jpa.repositories.TicketTypeJpaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicketPriceTest {
    @Autowired
    private TicketPriceJpaRepository ticketPriceJpaRepository;

    @Autowired
    private PricingCategoryJpaRepository pricingCategoryJpaRepository;

    @Autowired
    private TicketTypeJpaRepository ticketTypeJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFind() throws Exception {
        TicketPrice ticket = ticketPriceJpaRepository.getOne(1L);
        assertNotNull(ticket);
    }

    @Test
    public void testQueryAnnotation() throws Exception {
        List<TicketPrice> tickets = ticketPriceJpaRepository
                .getTicketsUnderPriceWithWorkshops(BigDecimal.valueOf(1000));
        assertTrue(tickets.size() > 0);
    }

    @Test
    public void testNamedQuery() throws Exception {
        List<TicketPrice> tickets = ticketPriceJpaRepository.namedFindTicketsByPricingCategoryName("Regular");
        assertTrue(tickets.size() > 0);
    }

    @Test
    public void testNamedNativeQuery() throws Exception {
        List<TicketPrice> tickets = ticketPriceJpaRepository.nativeFindTicketsByCategoryWithWorkshop("Regular");
        assertTrue(tickets.size() > 0);
    }

    @Test
    @Transactional
    public void testSaveAndGetAndDelete() throws Exception {
        TicketPrice ticketPrice = new TicketPrice();
        ticketPrice.setBasePrice(BigDecimal.valueOf(200, 2));

        ticketPrice.setPricingCategory(pricingCategoryJpaRepository.getOne("E"));

        ticketPrice.setTicketType(ticketTypeJpaRepository.getOne("C"));

        ticketPrice = ticketPriceJpaRepository.saveAndFlush(ticketPrice);

        // clear the persistence context so we don't return the previously cached
        // location object
        // this is a test only thing and normally doesn't need to be done in prod code
        entityManager.clear();

        TicketPrice savedTicketPrice = ticketPriceJpaRepository.getOne(ticketPrice.getTicketPriceId());
        assertEquals(BigDecimal.valueOf(200, 2), savedTicketPrice.getBasePrice());

        ticketPriceJpaRepository.deleteById(savedTicketPrice.getTicketPriceId());
    }
}
