package com.example.form;

import java.util.List;

/**
 * 商品情報を受け取るフォーム.
 * 
 * @author shota.suzuki
 *
 */
public class AddEditItemForm {

	/** 商品名 */
	private String name;

	/** 価格 */
	private String price;

	/** カテゴリー */
	private List<String> categoryList;

	/** ブランド */
	private String brand;

	/** 商品状態 */
	private String condition;

	/** 商品説明 */
	private String description;


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


	public List<String> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}


	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}


	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public String toString() {
		return "AddEditItemForm [name=" + name + ", price=" + price + ", categoryList=" + categoryList + ", brand="
				+ brand + ", condition=" + condition + ", description=" + description + "]";
	}

}