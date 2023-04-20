package com.ontrustserver.domain;

import com.ontrustserver.request.PostRequest;
import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String title;
    @Lob
    private String contents;

    public Post(PostRequest request){
        this.title = request.title();
        this.contents = request.contents();
    }
}
