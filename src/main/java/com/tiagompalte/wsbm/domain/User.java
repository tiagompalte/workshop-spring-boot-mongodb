package com.tiagompalte.wsbm.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Setter(value = AccessLevel.PRIVATE)
    @EqualsAndHashCode.Include
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    @DBRef(lazy = true)
    private List<Post> posts;

    public void addPost(Post post) {
        if(post == null) {
            return;
        }
        if(posts == null) {
            posts = new ArrayList<>();
        }
        posts.add(post);
    }

}
