package com.tiagompalte.wsbm.services;

import com.tiagompalte.wsbm.domain.Post;
import com.tiagompalte.wsbm.domain.User;
import com.tiagompalte.wsbm.dto.PostDTO;
import com.tiagompalte.wsbm.dto.UserDTO;
import com.tiagompalte.wsbm.repository.PostRepository;
import com.tiagompalte.wsbm.repository.UserRepository;
import com.tiagompalte.wsbm.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(String id) {
        Optional<Post> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public Post insert(Post post) {
        return repository.insert(post);
    }

    public static Post fromDTO(PostDTO dto) {
        User author = new User();
        if(dto.getAuthor() != null) author = UserService.fromDTO(dto.getAuthor());
        return new Post(dto.getId(), dto.getDate(), dto.getTitle(), dto.getBody(), author);
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public Post update(Post obj) {
        Post newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    private void updateData(Post newObj, Post obj) {
        newObj.setTitle(obj.getTitle());
        newObj.setBody(obj.getBody());
    }
}
