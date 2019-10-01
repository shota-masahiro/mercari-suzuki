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
	private String itemId;

	/** 商品名 */
	@NotBlank(message = "商品名を入力してください")
	private String itemName;

	/** 価格 */
	@Pattern(regexp = "^[0-9]+$", message = "半角数字で入力してください")
	@NotBlank(message = "価格を入力してください")
	private String price;

	/** ブランド */
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


	public Integer getIntegerItemId() {
		return Integer.parseInt(this.itemId);
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
		return "AddEditItemForm [itemId=" + itemId + ", itemName=" + itemName + ", price=" + price + ", brand=" + brand
				+ ", condition=" + condition + ", description=" + description + ", largeCategory=" + largeCategory
				+ ", mediumCategory=" + mediumCategory + ", smallCategory=" + smallCategory + ", shipping=" + shipping
				+ ", categoryId=" + categoryId + "]";
	}

}