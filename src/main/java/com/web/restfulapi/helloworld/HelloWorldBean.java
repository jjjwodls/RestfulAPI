package com.web.restfulapi.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//lombok으로 getter setter 사용하자.
@Data //setting에 annotaion processer 켜주자.
@AllArgsConstructor // 모든 생성자 생성해줌.
@NoArgsConstructor
public class HelloWorldBean {
    private String message;
}
