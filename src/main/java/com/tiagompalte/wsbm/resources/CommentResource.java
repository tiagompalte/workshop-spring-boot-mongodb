package com.tiagompalte.wsbm.resources;

import com.tiagompalte.wsbm.domain.Comment;
import com.tiagompalte.wsbm.dto.CommentDTO;
import com.tiagompalte.wsbm.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Calendar;
import java.util.TimeZone;

@RestController
@RequestMapping(value = "/comments")
public class CommentResource {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CommentDTO> findById(@PathVariable String id) {

        Comment comment = commentService.findById(id);
        return ResponseEntity.ok(new CommentDTO(comment));
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@RequestBody @Valid CommentDTO dto) {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        dto.setDate(calendar.getTime());

        Comment comment = commentService.fromDTO(dto);
        comment = commentService.insert(comment);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable String id, @RequestBody @Valid CommentDTO dto) {

        dto.setId(id);
        Comment comment = commentService.fromDTO(dto);
        comment = commentService.update(comment);
        return ResponseEntity.ok(new CommentDTO(comment));
    }
}
