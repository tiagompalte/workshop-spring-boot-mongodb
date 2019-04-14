package com.tiagompalte.wsbm.dto;

import com.tiagompalte.wsbm.domain.Comment;
import com.tiagompalte.wsbm.domain.Post;
import com.tiagompalte.wsbm.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "Título não informado")
    private String title;

    @NotBlank(message = "Texto não informado")
    private String body;

    @NotNull(message = "Autor não informado")
    private UserDTO author;

    private List<CommentDTO> comments;

    public PostDTO(Post post) {
        id = post.getId();
        date = post.getDate();
        title = post.getTitle();
        body = post.getBody();
        author = new UserDTO(post.getAuthor());
        comments = post.getComments().stream().map(c -> convertComment(c)).collect(Collectors.toList());
    }

    private CommentDTO convertComment(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setDate(comment.getDate());
        dto.setText(comment.getText());
        dto.setAuthor(new UserDTO(comment.getAuthor()));
        return dto;
    }
}
