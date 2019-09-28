package com.example.domain;

/**
 * Item情報を表すドメイン.
 * 
 * @author shota.suzuki
 *
 */
public class Item {

	/** ID */
	private Integer id;

	/** 商品名 */
	private String name;

	/** 価格 */
	private double price;

	/** カテゴリー */
	private String category;

	/** ブランド */
	private String brand;

	/** 商品状態 */
	private Integer condition;

	/** 商品説明 */
	private String description;

	/** 運送手段 */
	private Integer shipping;

	/** カテゴリーID */
	private Integer categoryId;

	/** 大カテゴリ */
	private String largeCategory;

	/** 中カテゴリ */
	private String mediumCategory;

	/** 小カテゴリ */
	private String smallCategory;

	/** 大カテゴリID */
	private Integer parentId;

	private Integer smallCategoryId;


	public Item() {

	}


	public Item(Integer id, String name, double price, String category, String brand, Integer condition,
			String description, Integer shipping, Integer categoryId, String largeCategory, String mediumCategory,
			String smallCategory, Integer parentId, Integer smallCategoryId) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.brand = brand;
		this.condition = condition;
		this.description = description;
		this.shipping = shipping;
		this.categoryId = categoryId;
		this.largeCategory = largeCategory;
		this.mediumCategory = mediumCategory;
		this.smallCategory = smallCategory;
		this.parentId = parentId;
		this.smallCategoryId = smallCategoryId;
	}


	public void setCategorys() {
		String[] categorys = this.category.split("/");
		this.setLargeCategory(categorys[0]);
		this.setMediumCategory(categorys[1]);
		this.setSmallCategory(categorys[2]);
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


	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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

	public int getIntPrice() {
		return (int)this.price;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
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


	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}


	public Integer getSmallCategoryId() {
		return smallCategoryId;
	}
	public void setSmallCategoryId(Integer smallCategoryId) {
		this.smallCategoryId = smallCategoryId;
	}


	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category + ", brand=" + brand
				+ ", condition=" + condition + ", description=" + description + ", shipping=" + shipping
				+ ", categoryId=" + categoryId + ", largeCategory=" + largeCategory + ", mediumCategory="
				+ mediumCategory + ", smallCategory=" + smallCategory + ", parentId=" + parentId + ", smallCategoryId="
				+ smallCategoryId + "]";
	}

}