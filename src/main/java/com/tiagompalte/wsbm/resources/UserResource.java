package com.tiagompalte.wsbm.resources;

import com.tiagompalte.wsbm.domain.Post;
import com.tiagompalte.wsbm.domain.User;
import com.tiagompalte.wsbm.dto.PostDTO;
import com.tiagompalte.wsbm.dto.UserDTO;
import com.tiagompalte.wsbm.services.UserService;
import com.tiagompalte.wsbm.services.exception.BadRequestException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> findAll() {

        List<UserDTO> list = userService.findAll().stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {

        User user = userService.findById(id);

        return ResponseEntity.ok(new UserDTO(user));
    }

    private void validate(User user) {

        if(Strings.isBlank(user.getEmail())) {
            throw new BadRequestException("Informe um e-mail");
        }

        if(Strings.isBlank(user.getName())) {
            throw new BadRequestException("Informe um nome");
        }
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@Valid @RequestBody UserDTO dto) {

        User user = userService.fromDTO(dto);
        validate(user);
        user = userService.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable String id, @Valid @RequestBody UserDTO dto) {

        dto.setId(id);
        User user = userService.fromDTO(dto);
        validate(user);
        user = userService.update(user);
        return ResponseEntity.ok(new UserDTO(user));
    }

    @GetMapping(value = "/{id}/posts")
    public ResponseEntity<List<PostDTO>> findPostByUser(@PathVariable String id) {

        User user = userService.findById(id);

        List<PostDTO> listPosts = new ArrayList<>();
        if(user.getPosts() != null) {
            listPosts = user.getPosts().stream().map(p -> converterPost(p)).collect(Collectors.toList());
        }
        return ResponseEntity.ok(listPosts);
    }

    private PostDTO converterPost(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setDate(post.getDate());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        return dto;
    }
}
