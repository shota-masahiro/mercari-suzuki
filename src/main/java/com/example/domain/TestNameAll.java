package com.example.domain;

public class TestNameAll {

	private String largeName;
	private String midiumName;
	private String smallName;


	public TestNameAll() {

	}
	public TestNameAll(String largeName, String midiumName, String smallName) {
		this.largeName = largeName;
		this.midiumName = midiumName;
		this.smallName = smallName;
	}


	public String getLargeMediumName() {
		return this.largeName+"/"+this.midiumName;
	}
	public String getLargeMediumSmallName() {
		return this.largeName+"/"+this.midiumName+"/"+this.smallName;
	}


	public String getLargeName() {
		return largeName;
	}
	public void setLargeName(String largeName) {
		this.largeName = largeName;
	}
	public String getMidiumName() {
		return midiumName;
	}
	public void setMidiumName(String midiumName) {
		this.midiumName = midiumName;
	}
	public String getSmallName() {
		return smallName;
	}
	public void setSmallName(String smallName) {
		this.smallName = smallName;
	}


	@Override
	public String toString() {
		return "TestNameAll [largeName=" + largeName + ", midiumName=" + midiumName + ", smallName=" + smallName + "]";
	}

}