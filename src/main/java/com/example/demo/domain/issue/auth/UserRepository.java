package com.example.demo.domain.issue.auth;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserRepository {

    @Select("select * from users where username = #{username}")
    Optional<User> findByUserName(String username);
}
