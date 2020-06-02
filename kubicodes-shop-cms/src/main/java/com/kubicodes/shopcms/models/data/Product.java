package com.kubicodes.shopcms.models.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 2, message="Name muss mindestens zwei Zeichen enthalten.")
	private String name;
	
	private String slug;
	
	@Size(min = 5, message="Beschreibung muss mindestens füng Zeichen enthalten.")
	private String description;
	
	private float price;
	
	private String image;
	
	@Column(name = "category_id")
	private int categoryId;
}
