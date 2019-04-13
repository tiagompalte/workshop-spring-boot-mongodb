package com.tiagompalte.wsbm.resources;

import com.tiagompalte.wsbm.domain.Comment;
import com.tiagompalte.wsbm.dto.CommentDTO;
import com.tiagompalte.wsbm.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/comments")
public class CommentResource {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CommentDTO> findById(@PathVariable String id) {

        Comment Comment = commentService.findById(id);
        return ResponseEntity.ok(new CommentDTO(Comment));
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@RequestBody CommentDTO dto) {

        Comment Comment = commentService.fromDTO(dto);
        Comment = commentService.insert(Comment);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Comment.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable String id, @RequestBody CommentDTO dto) {

        Comment Comment = commentService.fromDTO(dto);
        Comment.setId(id);
        Comment = commentService.update(Comment);
        return ResponseEntity.ok(new CommentDTO(Comment));
    }
}
