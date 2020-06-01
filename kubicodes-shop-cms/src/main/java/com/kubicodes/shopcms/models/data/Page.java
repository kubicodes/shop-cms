package com.kubicodes.shopcms.models.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="pages")
public class Page {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 2, message="Es müssen mindestens 2 Zeichen angegeben werden.")
	private String title;
	
	private String slug;
	
	@Size(min = 2, message="Es müssen mindestens 2 Zeichen angegeben werden.")
	private String content;
	
	
	//Constructors
	public Page(){
		
	}
	
	public Page(int id, String title, String slug, String content) {
		this.id = id;
		this.title = title;
		this.slug = slug;
		this.content = content;
	}

	//Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	//toString
	@Override
	public String toString() {
		return "Page [id=" + id + ", title=" + title + ", slug=" + slug + ", content=" + content + "]";
	}

	//Hashcode and equals based on ID
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
		Page other = (Page) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
