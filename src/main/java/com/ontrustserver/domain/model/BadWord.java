package com.ontrustserver.domain.model;

import com.ontrustserver.domain.model.enumerate.Language;
import jakarta.persistence.*;

@Entity
public class BadWord {
    @Id
    private String Sentence;
    @Enumerated(EnumType.STRING)
    private Language language;
    private int count;
    @PostLoad
    void updateCount(){
        this.count++;
    }
}
