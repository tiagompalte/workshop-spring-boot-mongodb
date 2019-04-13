package com.tiagompalte.wsbm.dto;

import com.tiagompalte.wsbm.domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Date date;

    private String title;

    private String body;

    private UserDTO author;

    private List<CommentDTO> comments;

    public PostDTO(Post post) {
        id = post.getId();
        date = post.getDate();
        title = post.getTitle();
        body = post.getBody();
        author = new UserDTO(post.getAuthor());
        comments = post.getListComments().stream().map(c -> new CommentDTO(c)).collect(Collectors.toList());
    }
}
