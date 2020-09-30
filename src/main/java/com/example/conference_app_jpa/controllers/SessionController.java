package com.example.conference_app_jpa.controllers;

import java.util.List;

import com.example.conference_app_jpa.models.Session;
import com.example.conference_app_jpa.repositories.SessionJpaRepository;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    @Autowired
    private SessionJpaRepository sessionRepository;

    @GetMapping
    public List<Session> listAllSessions() {
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session getSession(@PathVariable Long id) {
        Session existingSession = sessionRepository.getOne(id);
        return existingSession;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Session createSession(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteSession(@PathVariable Long id) {
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(CREATED)
    public Session updateSession(@PathVariable Long id, @RequestBody Session session) {
        Session existingSession = sessionRepository.getOne(id);
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }
}
