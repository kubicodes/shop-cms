package com.kubicodes.shopcms.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kubicodes.shopcms.models.data.Page;

public interface PageRepository extends JpaRepository<Page, Integer> {

	Page findBySlug(String slug);

	Page findBySlugAndIdNot(String slug, int id);

}
