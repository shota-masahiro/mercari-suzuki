package com.example.domain;

/**
 * カテゴリネームを表すドメイン.
 * 
 * @author shota.suzuki
 *
 */
public class CategoryName {
	
	/** カテゴリ大ネーム */
	private String categoryLargeName;
	
	/** カテゴリ中ネーム */
	private String categoryMediumName;
	
	/** カテゴリ小ネーム */
	private String categorySmallName;


	public CategoryName() {

	}

	public CategoryName(String categoryLargeName, String categoryMediumName, String categorySmallName) {
		this.categoryLargeName = categoryLargeName;
		this.categoryMediumName = categoryMediumName;
		this.categorySmallName = categorySmallName;
	}


	public String getCategoryLargeName() {
		return categoryLargeName;
	}
	public void setCategoryLargeName(String categoryLargeName) {
		this.categoryLargeName = categoryLargeName;
	}


	public String getCategoryMediumName() {
		return categoryMediumName;
	}
	public void setCategoryMediumName(String categoryMediumName) {
		this.categoryMediumName = categoryMediumName;
	}


	public String getCategorySmallName() {
		return categorySmallName;
	}
	public void setCategorySmallName(String categorySmallName) {
		this.categorySmallName = categorySmallName;
	}


	@Override
	public String toString() {
		return "CategoryName [categoryLargeName=" + categoryLargeName + ", categoryMediumName=" + categoryMediumName
				+ ", categorySmallName=" + categorySmallName + "]";
	}

}
