package com.tiagompalte.wsbm.services;

import com.tiagompalte.wsbm.domain.Comment;
import com.tiagompalte.wsbm.domain.Post;
import com.tiagompalte.wsbm.domain.User;
import com.tiagompalte.wsbm.dto.PostDTO;
import com.tiagompalte.wsbm.repository.PostRepository;
import com.tiagompalte.wsbm.services.exception.BadRequestException;
import com.tiagompalte.wsbm.services.exception.ObjectNotFoundException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private UserService userService;

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(String id) {
        Optional<Post> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    private void validate(Post post)
    {
        if(Strings.isBlank(post.getTitle()))
        {
            throw new BadRequestException("Informe um título");
        }

        if(Strings.isBlank(post.getBody()))
        {
            throw new BadRequestException("Informe um texto");
        }

        if(post.getAuthor() == null || Strings.isBlank(post.getAuthor().getId()))
        {
            throw new BadRequestException("Informe um usuário");
        }
    }

    public Post insert(Post post)
    {
        validate(post);
        User user = userService.findById(post.getAuthor().getId());
        post.setAuthor(user);
        post = repository.insert(post);
        userService.addPost(post);
        return post;
    }

    public static Post fromDTO(PostDTO dto) {
        User author = new User();
        if(dto.getAuthor() != null) author = UserService.fromDTO(dto.getAuthor());
        return new Post(dto.getId(), dto.getDate(), dto.getTitle(), dto.getBody(), author, new ArrayList<>());
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public Post update(Post obj) {
        Post newObj = findById(obj.getId());
        updateData(newObj, obj);
        validate(newObj);
        return repository.save(newObj);
    }

    private void updateData(Post newObj, Post obj) {
        newObj.setTitle(obj.getTitle());
        newObj.setBody(obj.getBody());
    }

    public List<Post> findByTitle(String text) {
        return repository.findByTitleContainingIgnoreCase(text);
    }

    public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
        return repository.fullSearch(text, minDate, maxDate);
    }

    public void addComment(Comment comment) {

        if(comment == null || comment.getPost() == null) {
            return;
        }

        Post post = findById(comment.getPost().getId());
        post.addComment(comment);
        repository.save(post);
    }
}
