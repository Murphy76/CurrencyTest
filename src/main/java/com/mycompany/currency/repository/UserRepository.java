package com.mycompany.currency.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.currency.model.User;

public interface  UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}