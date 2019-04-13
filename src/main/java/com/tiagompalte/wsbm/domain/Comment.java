package com.tiagompalte.wsbm.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    public String id;

    public Date date;

    public String text;

    @DBRef
    public User author;

    @DBRef
    public Post post;
}
