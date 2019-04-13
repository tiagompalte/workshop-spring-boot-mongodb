package com.tiagompalte.wsbm.resources;

import com.tiagompalte.wsbm.domain.Post;
import com.tiagompalte.wsbm.domain.User;
import com.tiagompalte.wsbm.dto.CommentDTO;
import com.tiagompalte.wsbm.dto.PostDTO;
import com.tiagompalte.wsbm.dto.UserDTO;
import com.tiagompalte.wsbm.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

    @Autowired
    private PostService postService;

    @GetMapping()
    public ResponseEntity<List<PostDTO>> findAll() {

        List<PostDTO> list = postService.findAll().stream().map(x -> new PostDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDTO> findById(@PathVariable String id) {

        Post post = postService.findById(id);
        return ResponseEntity.ok(new PostDTO(post));
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@RequestBody PostDTO dto) {

        Post post = postService.fromDTO(dto);
        post = postService.insert(post);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        postService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable String id, @RequestBody PostDTO dto) {

        Post post = postService.fromDTO(dto);
        post.setId(id);
        post = postService.update(post);
        return ResponseEntity.ok(new PostDTO(post));
    }

    @GetMapping(value = "/{id}/comments")
    public ResponseEntity<List<CommentDTO>> findPostByUser(@PathVariable String id) {

        Post post = postService.findById(id);
        List<CommentDTO> list = post.getListComments().stream().map(c -> new CommentDTO(c)).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
