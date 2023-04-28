package com.ontrustserver.domain.post;

import com.ontrustserver.request.PostRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;


@Getter
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

    @Builder
    public Post(String title, String contents){
        this.title = title;
        this.contents = contents;
    }
    public Post(PostRequest request){
        this.title = request.title();
        this.contents = request.contents();
    }

    //immutable Class
    public PostEditor.PostEditorBuilder toEditor() {
         return PostEditor.builder()
                .contents(title)
                .contents(contents);
    }

    public Post edit(PostEditor postEditor) {
        this.title = Optional
                .ofNullable(postEditor.title())
                .orElse(this.title);
        this.contents = Optional
                .ofNullable(postEditor.contents())
                .orElse(this.contents);

        return this;
    }
}
