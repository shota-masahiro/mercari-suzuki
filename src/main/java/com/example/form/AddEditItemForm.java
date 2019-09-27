package com.example.form;

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
	private String category;

	/** ブランド */
	private String brand;

	/** 商品状態 */
	private String condition;

	/** 商品説明 */
	private String description;

	/** 大カテゴリ */
	private String largeCategory;

	/** 中カテゴリ */
	private String mediumCategory;

	/** 小カテゴリ */
	private String smallCategory;


	public void setJoinCategory() {
		this.category = this.smallCategory;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public Integer getIntegerPrice() {
		return Integer.parseInt(this.price);
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}


	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}


	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}


	public Integer getIntegerCondition() {
		return Integer.parseInt(this.condition);
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


	public String getLargeCategory() {
		return largeCategory;
	}
	public void setLargeCategory(String largeCategory) {
		this.largeCategory = largeCategory;
	}


	public String getMediumCategory() {
		return mediumCategory;
	}
	public void setMediumCategory(String mediumCategory) {
		this.mediumCategory = mediumCategory;
	}


	public String getSmallCategory() {
		return smallCategory;
	}
	public void setSmallCategory(String smallCategory) {
		this.smallCategory = smallCategory;
	}


	@Override
	public String toString() {
		return "AddEditItemForm [name=" + name + ", price=" + price + ", category=" + category + ", brand=" + brand
				+ ", condition=" + condition + ", description=" + description + ", largeCategory=" + largeCategory
				+ ", mediumCategory=" + mediumCategory + ", smallCategory=" + smallCategory + "]";
	}

}