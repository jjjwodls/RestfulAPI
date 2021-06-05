package com.web.restfulapi.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    //User : Post -> 1 : (0~N), User : 주 데이터, Main : Sub -> 즉 parent와 child 관계
    @ManyToOne(fetch = FetchType.LAZY) //POST 입장에서는 유저 데이터가 하나가 와야 하므로.
    //LAZY를 주어 post 데이터가 조회되는 시점에 사용자 데이터를 가져오도록.
    @JsonIgnore
    private User user;

}
