package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.CategoryName;
import com.example.repository.ItemRepository;

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
	private ItemRepository itemRepository;
	
	// 動作確認用メソッド
	@RequestMapping("")
	public String index() {
		List<CategoryName> cList = itemRepository.categoryLargeText();
		System.out.println(cList);
		return "test";
	}
	
}
