package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 商品情報を受け取るフォーム.
 * 
 * @author shota.suzuki
 *
 */
public class AddEditItemForm {

	/** 商品ID */
	private String id;

	/** 商品名 */
	@NotBlank(message = "商品名を入力してください")
	private String name;

	/** 価格 */
	@Pattern(regexp = "^[0-9]+$", message = "半角数字で入力してください")
	@NotBlank(message = "価格を入力してください")
	private String price;

	/** カテゴリー */
	private String category;

	/** ブランド */
	@NotBlank(message = "ブランド名を入力してください")
	private String brand;

	/** 商品状態 */
	private String condition;

	/** 商品説明 */
	@NotBlank(message = "商品説明を入力してください")
	private String description;

	/** 大カテゴリ */
	private String largeCategory;

	/** 中カテゴリ */
	private String mediumCategory;

	/** 小カテゴリ */
	private String smallCategory;

	/** 運送手段 */
	private String shipping;

	/** カテゴリーID */
	private String categoryId;


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

	public Integer getIntegerSmallCategory() {
		return Integer.parseInt(this.smallCategory);
	}
	public String getSmallCategory() {
		return smallCategory;
	}
	public void setSmallCategory(String smallCategory) {
		this.smallCategory = smallCategory;
	}


	public Integer getIntegerId() {
		return Integer.parseInt(this.id);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	public Integer getIntegerShipping() {
		return Integer.parseInt(this.shipping);
	}
	public String getShipping() {
		return shipping;
	}
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}


	public Integer getIntegerCategoryId() {
		return Integer.parseInt(this.categoryId);
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	@Override
	public String toString() {
		return "AddEditItemForm [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category
				+ ", brand=" + brand + ", condition=" + condition + ", description=" + description + ", largeCategory="
				+ largeCategory + ", mediumCategory=" + mediumCategory + ", smallCategory=" + smallCategory
				+ ", shipping=" + shipping + ", categoryId=" + categoryId + "]";
	}

}