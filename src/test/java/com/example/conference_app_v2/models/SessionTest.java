package com.example.conference_app_jpa.models;

import com.example.conference_app_jpa.repositories.SessionJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SessionTest {
    @Autowired
    private SessionJpaRepository sessionJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFind() throws Exception {
        Session session = sessionJpaRepository.getOne(1L);
        assertNotNull(session);
    }

    @Test
    public void testSessionNameContains() throws Exception {
        List<Session> sessions = sessionJpaRepository.findBySessionNameContains("Java");
        assertTrue(sessions.size() > 0);
    }

    @Test
    public void testSessionNameStartsWith() throws Exception {
        List<Session> sessions = sessionJpaRepository.findBySessionNameStartingWith("Spring");
        assertTrue(sessions.size() > 0);
    }

    @Test
    public void testSessionNameEndingWith() throws Exception {
        List<Session> sessions = sessionJpaRepository.findBySessionNameEndingWith("Java");
        assertTrue(sessions.size() > 0);
    }

    @Test
    public void testFirstSessionContainingJava() throws Exception {
        Session session = sessionJpaRepository.findFirstBySessionNameContains("Java");
        assertEquals("Functional Programming in Java", session.getSessionName());
    }

    @Test
    public void testCountSessionsContainingJava() throws Exception {
        Long count = sessionJpaRepository.countBySessionNameContains("Java");
        assertEquals(9L, count);
    }

    @Test
    public void testSessionLengthNot() throws Exception {
        List<Session> sessions = sessionJpaRepository.findBySessionLengthNot(30);
        assertTrue(sessions.size() > 0);
    }

    @Test
    public void testSessionLengthLessThan() throws Exception {
        List<Session> sessions = sessionJpaRepository.findBySessionLengthLessThan(45);
        assertTrue(sessions.size() > 0);
    }

    @Test
    public void testPagingSorting() throws Exception {
        Page<Session> page = sessionJpaRepository.getSessionsWithName("S",
                PageRequest.of(1, 5, Sort.by(Sort.Direction.DESC, "sessionLength")));
        assertTrue(page.getTotalElements() > 0);
    }

    @Test
    public void testSessionNameNotLike() throws Exception {
        List<Session> sessions = sessionJpaRepository.findBySessionNameNotLike("Java%");
        assertTrue(sessions.size() > 0);
    }

    @Test
    public void testCustomImpl() throws Exception {
        List<Session> sessions = sessionJpaRepository.customGetSessions();
        assertTrue(sessions.size() > 0);
    }

    @Test
    @Transactional
    public void testSaveAndGetAndDelete() throws Exception {
        Session session = new Session();
        session.setSessionDescription("Introduction to spring boot");
        session.setSessionLength(5);
        session.setSessionName("Spring Boot Talk");
        session = sessionJpaRepository.saveAndFlush(session);

        // clear the persistence context so we don't return the previously cached
        // location object
        // this is a test only thing and normally doesn't need to be done in prod code
        entityManager.clear();

        Session otherSession = sessionJpaRepository.getOne(session.getSessionId());
        assertEquals("Spring Boot Talk", otherSession.getSessionName());

        sessionJpaRepository.deleteById(otherSession.getSessionId());
    }

}
