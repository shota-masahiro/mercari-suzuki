package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
		System.out.println(itemRepository.findById(2));
		return "test";
	}
	
}
