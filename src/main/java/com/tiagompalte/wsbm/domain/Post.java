package com.tiagompalte.wsbm.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "posts")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @EqualsAndHashCode.Include
    private String id;

    @NonNull
    private Date date;

    @NonNull
    private String title;

    @NonNull
    private String body;

    @DBRef
    @NonNull
    private User author;

    @DBRef(lazy = true)
    private List<Comment> listComments;

}
