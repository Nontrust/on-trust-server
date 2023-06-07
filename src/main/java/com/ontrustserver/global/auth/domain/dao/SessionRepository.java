package com.ontrustserver.global.auth.domain.dao;

import com.ontrustserver.domain.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByAccessToken(String authorization);
}
