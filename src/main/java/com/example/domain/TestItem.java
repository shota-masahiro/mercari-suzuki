package com.example.domain;

public class TestItem {

	/** itemID */
	private Integer itemId;

	/** 商品名 */
	private String itemName;

	/** 商品状態 */
	private Integer condition;

	/** カテゴリー */
	private Integer categoryId;

	/** ブランド */
	private String brand;

	/** 料金 */
	private Integer price;

	/** 配送手段 */
	private Integer shipping;

	/** 商品説明 */
	private String description;

	/** 大カテゴリID */
	private Integer largeCategoryId;

	/** 中カテゴリID */
	private Integer mediumCategoryId;

	/** 小カテゴリID */
	private Integer smallCategoryId;

	/** カテゴリー大中小 */
	private String nameAll;


	public TestItem() {

	}


	public TestItem(Integer itemId, String itemName, Integer condition, Integer categoryId, String brand, Integer price,
			Integer shipping, String description, Integer largeCategoryId, Integer mediumCategoryId,
			Integer smallCategoryId, String nameAll) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.condition = condition;
		this.categoryId = categoryId;
		this.brand = brand;
		this.price = price;
		this.shipping = shipping;
		this.description = description;
		this.largeCategoryId = largeCategoryId;
		this.mediumCategoryId = mediumCategoryId;
		this.smallCategoryId = smallCategoryId;
		this.nameAll = nameAll;
	}


	public String getLargeCategoryName() {
		return this.nameAll.split("/")[0];
	}
	public String getMediumCategoryName() {
		return this.nameAll.split("/")[1];
	}
	public String getSmallCategoryName() {
		return this.nameAll.split("/")[2];
	}


	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}


	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public Integer getCondition() {
		return condition;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}


	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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


	public Integer getLargeCategoryId() {
		return largeCategoryId;
	}
	public void setLargeCategoryId(Integer largeCategoryId) {
		this.largeCategoryId = largeCategoryId;
	}


	public Integer getMediumCategoryId() {
		return mediumCategoryId;
	}
	public void setMediumCategoryId(Integer mediumCategoryId) {
		this.mediumCategoryId = mediumCategoryId;
	}


	public Integer getSmallCategoryId() {
		return smallCategoryId;
	}
	public void setSmallCategoryId(Integer smallCategoryId) {
		this.smallCategoryId = smallCategoryId;
	}


	public String getNameAll() {
		return nameAll;
	}
	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}


	@Override
	public String toString() {
		return "TestItem [itemId=" + itemId + ", itemName=" + itemName + ", condition=" + condition + ", categoryId="
				+ categoryId + ", brand=" + brand + ", price=" + price + ", shipping=" + shipping + ", description="
				+ description + ", largeCategoryId=" + largeCategoryId + ", mediumCategoryId=" + mediumCategoryId
				+ ", smallCategoryId=" + smallCategoryId + ", nameAll=" + nameAll + "]";
	}

}
