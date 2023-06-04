package com.ontrustserver.domain.model;

import com.ontrustserver.global.util.TimeUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public class BaseEntity {
    /**
     * Date는 UTC로 저장, 클라이언트에서 KTC를 변환하도록 구현합니다.
     * 차후 EC2 Instance의 resion에 구애받지 않고
     * 외국에서 서비스 시 현지 시간에 맞도록 조정해 쓰기 위함입니다.
     */
    //Todo: ZonedDateTime ToString Overriding으로 포메팅 예정
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected ZonedDateTime createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected ZonedDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = TimeUtil.nowUtcDate();
        this.updatedDate = this.createdDate;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = TimeUtil.nowUtcDate();
    }
}
