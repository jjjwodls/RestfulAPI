package com.web.restfulapi.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloWorldController {

    @GetMapping(path = "/hello-world") // end point
    public String helloWorld(){
        log.info("hello word log");
        return "hello world";
    }

    //alt+enter 로 들어감
    @GetMapping(path = "/hello-world-bean") // end point
    public HelloWorldBean helloWorldBean(){
        log.info("hello word bean");
        return new HelloWorldBean("hello world");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}") // end point , 컬리브레이스?
    public HelloWorldBean helloWorldBean(@PathVariable String name){ //변수값과 동일하게 정의해야함. pathvariable 값과.
        log.info("path variable = " + name);
        return new HelloWorldBean(String.format("Hello World,%s",name));
    }
}
