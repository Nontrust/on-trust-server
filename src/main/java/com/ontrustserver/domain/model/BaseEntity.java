package com.ontrustserver.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedDate;

    // Todo: UTC Time Stamp 적용 필요 시 변경 예정
    @PrePersist
    protected void onCreate() {
        this.createdDate = Date.from(Instant.now());
        this.updatedDate = this.createdDate;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = Date.from(Instant.now());
    }
}
