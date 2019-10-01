package com.example.domain;

/**
 * カテゴリネームを表すドメイン.
 * 
 * @author shota.suzuki
 *
 */
public class CategoryName {

	/** 商品ID */
	private Integer id;

	/** parentID */
	private Integer parentId;

	/** カテゴリ大中小ネーム */
	private String categoryName;

	/** カテゴリー名 */
	private String nameAll;

	/** カテゴリ大ネーム */
	private String categoryLargeName;

	/** カテゴリ中ネーム */
	private String categoryMediumName;

	/** カテゴリ小ネーム */
	private String categorySmallName;


	public CategoryName() {

	}


	public CategoryName(Integer id, Integer parentId, String categoryName, String nameAll, String categoryLargeName,
			String categoryMediumName, String categorySmallName) {
		this.id = id;
		this.parentId = parentId;
		this.categoryName = categoryName;
		this.nameAll = nameAll;
		this.categoryLargeName = categoryLargeName;
		this.categoryMediumName = categoryMediumName;
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


	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getNameAll() {
		return nameAll;
	}
	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
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
		return "CategoryName [id=" + id + ", parentId=" + parentId + ", categoryName=" + categoryName + ", nameAll="
				+ nameAll + ", categoryLargeName=" + categoryLargeName + ", categoryMediumName=" + categoryMediumName
				+ ", categorySmallName=" + categorySmallName + "]";
	}

}