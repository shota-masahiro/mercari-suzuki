package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.TestNameAll;
import com.example.repository.TestRepository;

/**
 * 動作確認用
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestRepository testRepository;

	//1ページ30商品を追加
	public static final int ROW_PAR_PAGE = 30;

	@RequestMapping("")
	public String index() {

		Integer arrow = 0;
		String itemName = null;
		String brand = null;
		String countPage = "";

		Integer[] categoryIds = {12, null, null};
		System.out.println("categoryIds:"+categoryIds);
		TestNameAll nameAll = testRepository.searchName(categoryIds);
		testRepository.search(arrow, itemName, nameAll, brand, countPage).forEach(System.out::println);

		countPage = "count";
		Integer countPages = testRepository.searchCount(arrow, itemName, nameAll, brand, countPage);
		int maxPage = countPages / ROW_PAR_PAGE;
		if (countPages % ROW_PAR_PAGE != 0) {
			maxPage++;
		}
		System.out.println("ページ数:"+maxPage);

		return "test";
	}

}