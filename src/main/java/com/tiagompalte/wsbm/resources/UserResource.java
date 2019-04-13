package com.tiagompalte.wsbm.resources;

import com.tiagompalte.wsbm.domain.User;
import com.tiagompalte.wsbm.dto.UserDTO;
import com.tiagompalte.wsbm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping()
    public ResponseEntity<Void> insert(@RequestBody UserDTO dto) {

        User user = userService.fromDTO(dto);
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
    public ResponseEntity<UserDTO> update(@PathVariable String id, @RequestBody UserDTO dto) {

        User user = userService.fromDTO(dto);
        user.setId(id);
        user = userService.update(user);
        return ResponseEntity.ok(new UserDTO(user));
    }

}
