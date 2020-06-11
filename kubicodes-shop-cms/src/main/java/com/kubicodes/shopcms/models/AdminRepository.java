package com.kubicodes.shopcms.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kubicodes.shopcms.models.data.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

	Admin findByUsername(String username);
}
