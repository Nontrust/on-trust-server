package com.ontrustserver.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.UUID.randomUUID;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String accessToken;

    @ManyToOne
    private Account account;

    @Builder
    public Session(Account account) {
        this.accessToken = randomUUID().toString();
        this.account = account;
    }
}

