package com.web.restfulapi.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retriveAllUsers() {
        return service.findAll();
    }

    /**
     *
     * @param id 조회할 id
     * @return Hetaos를 통해 다른 사용자들이 추가 URL으로 다른 동작을 할 수 있는것을 정의함.
     */
    @GetMapping("/users/{id}")
    public EntityModel<User> retriveUser(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        //HATEOAS 작업 진행.
        EntityModel<User> model = EntityModel.of(user); //현재 스프링부트 최신버전.
        WebMvcLinkBuilder linkTo2 = linkTo(methodOn(this.getClass()).retriveAllUsers());

        //해당 작업을 통해 all-users 조회에 대한 URL이 추가된다.
        model.add(linkTo2.withRel("all-users"));

        return model;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand((savedUser.getId()))
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }


}
