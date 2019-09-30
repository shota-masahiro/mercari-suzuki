package com.example;

public class BooleanTest {

	public static void main(String[] args) {
		
		
		String nameAll = "Men/Top/PPP";
		
		String largeName = nameAll.split("/")[0];
		String mediumName = nameAll.split("/")[1];
		String smallName = nameAll.split("/")[2];
		System.out.println("largeName:"+largeName);
		System.out.println("mediumName:"+mediumName);
		System.out.println("smallName:"+smallName);
		

		String brand = "", itemName = "";
		String largeCategory = null, mediumCategory = null, smallCategory = null;
		String[] categorys = {largeCategory, mediumCategory, smallCategory};
		
		StringBuilder sql = new StringBuilder();
		sql.append("test aa");
		
		StringBuilder checkSQL = new StringBuilder(sql);
		checkSQL.append("   testest");
		System.out.println(checkSQL);

		boolean key = true;

		if (itemName != null && categorys[2] != null && brand != null && key) {
			System.out.println("商品名 + 大中小カテゴリ + ブランド名");
			key = false;
		}

		if (itemName != null && categorys[1] != null && brand != null && key) {
			System.out.println("商品名 + 大中カテゴリ + ブランド名");
			key = false;
		}

		if (itemName != null && categorys[0] != null && brand != null && key) {
			System.out.println("商品名 + 大カテゴリ + ブランド名");
			key = false;
		}

		if (itemName != null && categorys[2] != null && key) {
			System.out.println("商品名 + 大中小カテゴリで検索");
			key = false;
		}

		if (itemName != null && categorys[1] != null && key) {
			System.out.println("商品名 + 大中カテゴリで検索");
			key = false;
		}

		if (itemName != null && categorys[0] != null && key) {
			System.out.println("商品名 + 大カテゴリで検索");
			key = false;
		}

		if (categorys[2] != null && brand != null && key) {
			System.out.println("大中小カテゴリ + ブランド名");
			key = false;
		}

		if (categorys[1] != null && brand != null && key) {
			System.out.println("大中カテゴリ + ブランド名");
			key = false;
		}

		if (categorys[0] != null && brand != null && key) {
			System.out.println("大カテゴリ + ブランド名");
			key = false;
		}

		if (itemName != null && brand != null && key) {
			System.out.println("商品名 + ブランド名で検索");
			key = false;
		}

		if (categorys[2] != null && key) {
			System.out.println("大中小カテゴリのみで検索");
			key = false;
		}

		if (categorys[1] != null && key) {
			System.out.println("大中カテゴリのみで検索");
			key = false;
		}

		if (categorys[0] != null && key) {
			System.out.println("大カテゴリのみで検索");
			key = false;
		}

		if (brand != null && key) {
			System.out.println("ブランド名のみで検索");
			key = false;
		}

		if (itemName != null && key) {
			System.out.println("商品名のみで検索");
			key = false;
		}

	}
}
