package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品登録画面を操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/add")
public class AddItemController {
	
	/**
	 * 商品登録画面を出力します.
	 * 
	 * @return 商品登録画面
	 */
	@RequestMapping("")
	public String add() {
		return "add";
	}
	
}
