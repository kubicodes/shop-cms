package com.kubicodes.shopcms.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kubicodes.shopcms.models.data.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Product findBySlug(String slug);

	Product findBySlugAndIdNot(String slug, int id);

	List<Product> findAllByCategoryId(String CategoryId);

}
