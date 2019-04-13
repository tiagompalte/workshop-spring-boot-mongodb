package com.tiagompalte.wsbm.services;

import com.tiagompalte.wsbm.domain.Comment;
import com.tiagompalte.wsbm.domain.Post;
import com.tiagompalte.wsbm.domain.User;
import com.tiagompalte.wsbm.dto.CommentDTO;
import com.tiagompalte.wsbm.repository.CommentRepository;
import com.tiagompalte.wsbm.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    public Comment findById(String id) {
        Optional<Comment> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public Comment insert(Comment comment) {
        return repository.insert(comment);
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
        return repository.save(newObj);
    }
}
