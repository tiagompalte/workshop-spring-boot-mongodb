package com.tiagompalte.wsbm.services;

import com.tiagompalte.wsbm.domain.Post;
import com.tiagompalte.wsbm.domain.User;
import com.tiagompalte.wsbm.dto.UserDTO;
import com.tiagompalte.wsbm.repository.UserRepository;
import com.tiagompalte.wsbm.services.exception.BadRequestException;
import com.tiagompalte.wsbm.services.exception.ObjectNotFoundException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    private void validate(User user) {

        if(Strings.isBlank(user.getName())) {
            throw new BadRequestException("Informe um nome");
        }

        if(Strings.isBlank(user.getEmail())) {
            throw new BadRequestException("Informe um e-mail");
        }

    }

    public User insert(User user) {

        validate(user);
        try {
            return repository.insert(user);
        }
        catch (DuplicateKeyException e) {
            throw new BadRequestException(String.format("O e-mail %s já existe", user.getEmail()));
        }
    }

    public static User fromDTO(UserDTO dto) {
        return new User(dto.getId(), dto.getName(), dto.getEmail(), new ArrayList<>());
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public User update(User obj) {
        User newObj = findById(obj.getId());
        updateData(newObj, obj);
        validate(newObj);
        return repository.save(newObj);
    }

    public void addPost(Post post) {

        if(post == null || post.getAuthor() == null) {
            return;
        }

        User user = findById(post.getAuthor().getId());
        user.addPost(post);

        repository.save(user);
    }

    private void updateData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }
}
