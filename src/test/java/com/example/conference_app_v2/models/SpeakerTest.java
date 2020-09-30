package com.example.conference_app_jpa.models;

import com.example.conference_app_jpa.repositories.SpeakerJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SpeakerTest {
    @Autowired
    private SpeakerJpaRepository speakerJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFind() throws Exception {
        Speaker speaker = speakerJpaRepository.getOne(1L);
        assertNotNull(speaker);
    }

    @Test
    public void testSpeakerPhotoNull() throws Exception {
        List<Speaker> speakers = speakerJpaRepository.findBySpeakerPhotoNull();
        assertTrue(speakers.size() > 0);
    }

    @Test
    public void testOrderByFirstNameAsc() throws Exception {
        List<Speaker> speakers = speakerJpaRepository.findByLastNameOrderByFirstNameAsc("Clark");
        assertTrue(speakers.size() > 0);
    }

    @Test
    public void testCompanyIgnoreCase() throws Exception {
        List<Speaker> speakers = speakerJpaRepository.findByCompanyIgnoreCase("national bank");
        assertTrue(speakers.size() > 0);
    }

    @Test
    public void testFindByFirstNameAndLastName() {
        List<Speaker> speakers = speakerJpaRepository.findByFirstNameAndLastName("Justin", "Clark");
        assertTrue(speakers.size() > 0);
    }

    @Test
    public void testFindByCompaniesIn() {
        List<String> companies = new ArrayList<String>();
        companies.add("National Bank");
        companies.add("Contoso");
        List<Speaker> speakers = speakerJpaRepository.findByCompanyIn(companies);
        assertTrue(speakers.size() > 0);
    }

    @Test
    public void testFindByFirstNameOrLastName() {
        List<Speaker> speakers = speakerJpaRepository.findByFirstNameOrLastName("Justin", "Clark");
        assertTrue(speakers.size() > 0);
    }

    @Test
    @Transactional
    public void testSaveAndGetAndDelete() throws Exception {
        Speaker speaker = new Speaker();
        speaker.setCompany("Pluralsight");
        speaker.setFirstName("Dan");
        speaker.setLastName("Bunker");
        speaker.setTitle("Author");
        speaker.setSpeakerBio("Consulting and mentoring");
        speaker = speakerJpaRepository.saveAndFlush(speaker);

        // clear the persistence context so we don't return the previously cached
        // location object
        // this is a test only thing and normally doesn't need to be done in prod code
        entityManager.clear();

        Speaker otherSpeaker = speakerJpaRepository.getOne(speaker.getSpeakerId());
        assertEquals("Dan", otherSpeaker.getFirstName());

        speakerJpaRepository.deleteById(otherSpeaker.getSpeakerId());
    }

}
