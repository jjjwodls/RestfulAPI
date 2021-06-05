package com.web.restfulapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class JavaJpaController {

    @Autowired
    public UserRepository userRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
       return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id); //Optional 데이터는 데이터가 존재 할수도 안할수도 있기 때문에 Optional로 반환해줘야 한다.

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("[Id[%s] not found",id));
        }

        EntityModel<User> model = EntityModel.of(user.get()); //현재 스프링부트 최신버전.
        WebMvcLinkBuilder linkTo2 = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        //해당 작업을 통해 all-users 조회에 대한 URL이 추가된다.
        model.add(linkTo2.withRel("all-users"));

        //return user.get(); //user 타입의 데이터로 전환함. Optional 에서 데이터 꺼내서 그냥 반환 할 때
        return model;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


}
