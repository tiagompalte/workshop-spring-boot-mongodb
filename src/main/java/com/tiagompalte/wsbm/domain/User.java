package com.tiagompalte.wsbm.domain;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @EqualsAndHashCode.Include
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @DBRef(lazy = true)
    private List<Post> posts;

}
