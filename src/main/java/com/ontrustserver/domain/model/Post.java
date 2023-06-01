package com.ontrustserver.domain.model;

import com.ontrustserver.domain.post.dto.request.PostRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;


@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Post extends BaseEntity{
    // todo Generation Type SEQUENCE로 변경 예정
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String title;
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

    public void edit(PostEditor postEditor) {
        this.title = Optional
                .ofNullable(postEditor.title())
                .orElse(this.title);
        this.contents = Optional
                .ofNullable(postEditor.contents())
                .orElse(this.contents);
    }
}
