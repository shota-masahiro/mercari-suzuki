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

	/** 商品ID */
	private Integer id;

	/** parentID */
	private Integer parentId;


	public CategoryName() {

	}


	public CategoryName(String categoryLargeName, String categoryMediumName, String categorySmallName, Integer id,
			Integer parentId) {
		this.categoryLargeName = categoryLargeName;
		this.categoryMediumName = categoryMediumName;
		this.categorySmallName = categorySmallName;
		this.id = id;
		this.parentId = parentId;
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


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}


	@Override
	public String toString() {
		return "CategoryName [categoryLargeName=" + categoryLargeName + ", categoryMediumName=" + categoryMediumName
				+ ", categorySmallName=" + categorySmallName + ", id=" + id + ", parentId=" + parentId + "]";
	}

}
