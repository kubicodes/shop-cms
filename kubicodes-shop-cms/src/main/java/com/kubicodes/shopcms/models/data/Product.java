package com.kubicodes.shopcms.models.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 2, message = "Name muss mindestens zwei Zeichen enthalten.")
	private String name;

	private String slug;

	@Size(min = 5, message = "Beschreibung muss mindestens füng Zeichen enthalten.")
	private String description;

	@Pattern(regexp = "^[0-9]+([.][0-9]{1,2})?", message = "Keine gültige Preisangabe")
	private String price;

	private String image;

	@Pattern(regexp = "^[1-9][0-9]*", message = "Keine Kategorie ausgewählt")
	@Column(name = "category_id")
	private String categoryId;

	
	//Constructors
	public Product() {
		
	}
	
	public Product(int id, String name, String slug, String description, String price, String image, String categoryId) {
		this.id = id;
		this.name = name;
		this.slug = slug;
		this.description = description;
		this.price = price;
		this.image = image;
		this.categoryId = categoryId;
		
	}

	//Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	//toString
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", slug=" + slug + ", description=" + description + ", price="
				+ price + ", image=" + image + ", categoryId=" + categoryId + "]";
	}

	//hashCode and equals based on Id
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
