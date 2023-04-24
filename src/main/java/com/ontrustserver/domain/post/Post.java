package com.ontrustserver.domain.post;

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
    // todo Generation Type SEQUENCE로 변경 예정
    @GeneratedValue(strategy = GenerationType.AUTO)
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
