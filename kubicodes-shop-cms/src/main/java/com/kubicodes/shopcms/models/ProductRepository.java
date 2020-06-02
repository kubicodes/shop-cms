package com.kubicodes.shopcms.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kubicodes.shopcms.models.data.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
