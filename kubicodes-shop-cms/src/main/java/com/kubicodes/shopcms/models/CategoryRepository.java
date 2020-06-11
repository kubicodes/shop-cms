package com.kubicodes.shopcms.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kubicodes.shopcms.models.data.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Category findBySlug(String slug);

	Category findBySlugAndIdNot(String slug, int id);
	
}
