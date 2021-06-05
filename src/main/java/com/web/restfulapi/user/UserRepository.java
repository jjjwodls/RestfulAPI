package com.web.restfulapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> { //두번째 매개변수는 기본키의 타입이 들어간다.
}
