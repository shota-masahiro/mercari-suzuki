package com.example;

public class Test {

	public static void main(String[] args) {
		
		String category = "shota/suzuki/masahiro";
		
		String[] categorys = category.split("/");
		
		for (String s : categorys) {
			System.out.println(s);
		}

	}

}
