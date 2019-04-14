package com.tiagompalte.wsbm.services;

import com.tiagompalte.wsbm.domain.Comment;
import com.tiagompalte.wsbm.domain.Post;
import com.tiagompalte.wsbm.domain.User;
import com.tiagompalte.wsbm.dto.CommentDTO;
import com.tiagompalte.wsbm.repository.CommentRepository;
import com.tiagompalte.wsbm.services.exception.BadRequestException;
import com.tiagompalte.wsbm.services.exception.ObjectNotFoundException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    public Comment findById(String id) {
        Optional<Comment> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    private void validate(Comment comment) {

        if(Strings.isBlank(comment.getText())) {
            throw new BadRequestException("Informe um texto");
        }

        if(comment.getAuthor() == null || Strings.isBlank(comment.getAuthor().getId())) {
            throw new BadRequestException("Informe um autor");
        }

        if(comment.getPost() == null || Strings.isBlank(comment.getPost().getId())) {
            throw new BadRequestException("Informe um post");
        }

    }

    public Comment insert(Comment comment) {

        validate(comment);

        Post post = postService.findById(comment.getPost().getId());
        comment.setPost(post);

        User user = userService.findById(comment.getAuthor().getId());
        comment.setAuthor(user);

        comment = repository.insert(comment);

        postService.addComment(comment);

        return comment;
    }

    public static Comment fromDTO(CommentDTO dto) {
        User author = new User();
        if (dto.getAuthor() != null) author = UserService.fromDTO(dto.getAuthor());
        Post post = new Post();
        if (dto.getPost() != null) post = PostService.fromDTO(dto.getPost());
        return new Comment(dto.getId(), dto.getDate(), dto.getText(), author, post);
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public Comment update(Comment obj) {
        Comment newObj = findById(obj.getId());
        newObj.setText(obj.getText());
        validate(newObj);
        return repository.save(newObj);
    }
}
