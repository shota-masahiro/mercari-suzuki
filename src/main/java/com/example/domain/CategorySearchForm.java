package com.example.domain;

/**
 * カテゴリ検索の値を受け取るフォーム
 * 
 * @author shota.suzuki
 *
 */
public class CategorySearchForm {
	
	/** 商品名 */
	private String itemName;
	
	/** 大カテゴリ */
	private String largeCategory;
	
	/** 中カテゴリ */
	private String mediumCategory;
	
	/** 小カテゴリ */
	private String smallCategory;
	
	/** ブランド名 */
	private String brand;


	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
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


	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}


	@Override
	public String toString() {
		return "CategorySearchForm [itemName=" + itemName + ", largeCategory=" + largeCategory + ", mediumCategory="
				+ mediumCategory + ", smallCategory=" + smallCategory + ", brand=" + brand + "]";
	}

}
