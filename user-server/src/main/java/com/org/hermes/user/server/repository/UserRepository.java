package com.org.hermes.user.server.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.hermes.user.server.repository.entity.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    //User findByEmail(String email);
    User findByFirstName(String firstName);
    User findByCreatedDateBetween(LocalDateTime fromdate, LocalDateTime todate);
    User findByMobile(String phoneNum);
    User findByStatus(int status);
    //User findByUserName(String userName);
}