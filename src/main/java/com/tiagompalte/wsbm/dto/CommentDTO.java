package com.tiagompalte.wsbm.dto;

import com.tiagompalte.wsbm.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String text;

    private Date date;

    private UserDTO author;

    private PostDTO post;

    public CommentDTO(Comment commet) {
        id = commet.getId();
        text = commet.getText();
        date = commet.getDate();
        author = new UserDTO(commet.getAuthor());
        post = new PostDTO(commet.getPost());
    }

}
