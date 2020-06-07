package com.kubicodes.shopcms.models.data;

public class Cart {
	
	private int id;
	private String name;
	private String price;
	private int quantity;
	private String image;
	
	
	//Constructors
	public Cart() {
		
	}
	
	public Cart(int id, String name, String price, int quantity, String image) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	//toString
	@Override
	public String toString() {
		return "Cart [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", image=" + image
				+ "]";
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
		Cart other = (Cart) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
	
	

}
