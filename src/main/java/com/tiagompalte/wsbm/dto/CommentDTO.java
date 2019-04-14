package com.tiagompalte.wsbm.dto;

import com.tiagompalte.wsbm.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @NotBlank(message = "Texto não informado")
    private String text;

    private Date date;

    @NotNull(message = "Autor não informado")
    private UserDTO author;

    @NotNull(message = "Post não informado")
    private PostDTO post;

    public CommentDTO(Comment comment) {
        id = comment.getId();
        text = comment.getText();
        date = comment.getDate();
        author = new UserDTO(comment.getAuthor());
        post = new PostDTO(comment.getPost());
    }

}
