package com.example.conference_app_jpa.repositories;

import java.util.List;

import com.example.conference_app_jpa.models.Session;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessionJpaRepository extends JpaRepository<Session, Long>, SessionCustomJpaRepository {
    List<Session> findBySessionNameContains(String sessionName);

    List<Session> findBySessionNameStartingWith(String sessionName);

    List<Session> findBySessionNameEndingWith(String sessionName);

    Session findFirstBySessionNameContains(String sessionName);

    Long countBySessionNameContains(String sessionName);

    List<Session> findBySessionLengthNot(Integer sessionLength);

    List<Session> findBySessionLengthLessThan(Integer sessionLength);

    List<Session> findBySessionNameNotLike(String sessionName);

    @Query("select session from Session session where session.sessionName like %:name")
    Page<Session> getSessionsWithName(@Param("name") String sessionName, Pageable pageable);
}
