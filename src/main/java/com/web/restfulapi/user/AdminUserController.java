package com.web.restfulapi.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retriveAllUsers() {

        List<User> users = service.findAll();


        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "password", "ssn");
        MappingJacksonValue mapping = new MappingJacksonValue(users);

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
        mapping.setFilters(filters);

        return mapping;
    }

    //GET /admin/users/1 -> /admin/v1/users/1
//    @GetMapping("/v1/users/{id}")  // url로 버전관리 (1)
//    @GetMapping(value = "/users/{id}/", params = "version=1") 파라미터를 통한 버전관리 (2)
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1") //헤더값으로 버전관리 (3)
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json") // (4) producer를 이용한 mime타입으로 버전관리
    public MappingJacksonValue retriveUserV1(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "password", "ssn");
        MappingJacksonValue mapping = new MappingJacksonValue(user);

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
        mapping.setFilters(filters);

        return mapping;
    }

    //    @GetMapping("/v2/users/{id}")
//    @GetMapping(value = "/users/{id}/", params = "version=2")
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2") //header의 key,value 형식으로 이루어진다.
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retriveUserV2(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> User2 로 변환하자.
        UserV2 userV2 = new UserV2();
        //id, joinDate, name, password, ssn
        BeanUtils.copyProperties(user, userV2); //공통적인 필드값이 있는 경우 카피해준다. 필드값 복사.
        userV2.setGrade("VIP");


        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");
        MappingJacksonValue mapping = new MappingJacksonValue(userV2);

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);
        mapping.setFilters(filters);

        return mapping;
    }


}
