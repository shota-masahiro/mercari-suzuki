package com.example.domain;

/**
 * カテゴリ検索の値を受け取るフォーム
 * 
 * @author shota.suzuki
 *
 */
public class CategorySearchForm {

	/** 商品名 */
	private String itemNameForm;

	/** 大カテゴリ */
	private String largeCategoryForm;

	/** 中カテゴリ */
	private String mediumCategoryForm;

	/** 小カテゴリ */
	private String smallCategoryForm;

	/** ブランド名 */
	private String brandForm;


	public String getItemNameForm() {
		return itemNameForm;
	}
	public void setItemNameForm(String itemNameForm) {
		this.itemNameForm = itemNameForm;
	}


	public String getLargeCategoryForm() {
		return largeCategoryForm;
	}
	public void setLargeCategoryForm(String largeCategoryForm) {
		this.largeCategoryForm = largeCategoryForm;
	}


	public String getMediumCategoryForm() {
		return mediumCategoryForm;
	}
	public void setMediumCategoryForm(String mediumCategoryForm) {
		this.mediumCategoryForm = mediumCategoryForm;
	}


	public String getSmallCategoryForm() {
		return smallCategoryForm;
	}
	public void setSmallCategoryForm(String smallCategoryForm) {
		this.smallCategoryForm = smallCategoryForm;
	}


	public String getBrandForm() {
		return brandForm;
	}
	public void setBrandForm(String brandForm) {
		this.brandForm = brandForm;
	}


	@Override
	public String toString() {
		return "CategorySearchForm [itemNameForm=" + itemNameForm + ", largeCategoryForm=" + largeCategoryForm
				+ ", mediumCategoryForm=" + mediumCategoryForm + ", smallCategoryForm=" + smallCategoryForm
				+ ", brandForm=" + brandForm + "]";
	}

}
