package com.kubicodes.shopcms.models.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String slug;
	
	@Size(min=2, message="Es müssen mindestens 2 Zeichen angegeben werden.")
	private String title;
	
	//Constructors
	public Category() {
		
	}
	
	public Category(int id, String slug, String title) {
		this.id = id;
		this.slug = slug;
		this.title = title;
	}

	//Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	//toString
	@Override
	public String toString() {
		return "Category [id=" + id + ", slug=" + slug + ", title=" + title + "]";
	}

	//hashCode and equals based on ID
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
		Category other = (Category) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
	
}
