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

	@RequestMapping("")
	public String index() {

		Integer arrow = 0;
		String itemName = "Nike";
		String brand = "Nike";

		Integer[] categoryIds = {15, null, null};
		TestNameAll nameAll = testRepository.searchName(categoryIds);

		testRepository.search(arrow, itemName, nameAll, brand).forEach(System.out::println);

		return "test";
	}

}