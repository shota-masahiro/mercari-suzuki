package com.example.domain;

public class TestItem {

	/** itemID */
	private Integer id;

	/** 商品名 */
	private String name;

	/** 商品状態 */
	private Integer condition;

	/** カテゴリー */
	private Integer category;

	/** ブランド */
	private String brand;

	/** 料金 */
	private Integer price;

	/** 配送手段 */
	private Integer shipping;

	/** 商品説明 */
	private String description;

	/** カテゴリー大中小 */
	private String nameAll;


	public TestItem() {

	}


	public TestItem(Integer id, String name, Integer condition, Integer category, String brand, Integer price,
			Integer shipping, String description, String nameAll) {
		this.id = id;
		this.name = name;
		this.condition = condition;
		this.category = category;
		this.brand = brand;
		this.price = price;
		this.shipping = shipping;
		this.description = description;
		this.nameAll = nameAll;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public Integer getCondition() {
		return condition;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}


	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}


	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}


	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}


	public Integer getShipping() {
		return shipping;
	}
	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}


	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	public String getNameAll() {
		return nameAll;
	}
	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}


	@Override
	public String toString() {
		return "TestForm [id=" + id + ", name=" + name + ", condition=" + condition + ", category=" + category
				+ ", brand=" + brand + ", price=" + price + ", shipping=" + shipping + ", description=" + description
				+ ", nameAll=" + nameAll + "]";
	}

}
