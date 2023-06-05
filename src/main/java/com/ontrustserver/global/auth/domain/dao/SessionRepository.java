package com.ontrustserver.global.auth.domain.dao;

import com.ontrustserver.domain.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
