package com.example.conference_app_jpa.repositories;

import java.util.List;

import com.example.conference_app_jpa.models.Session;

public interface SessionCustomJpaRepository {
    List<Session> customGetSessions();
}
