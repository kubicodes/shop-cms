package com.kubicodes.shopcms.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kubicodes.shopcms.models.data.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
