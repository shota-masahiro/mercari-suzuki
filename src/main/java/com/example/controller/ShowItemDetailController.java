package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemDetailService;

/**
 * 商品詳細画面を操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/detail")
public class ShowItemDetailController {

	@Autowired
	private ShowItemDetailService showItemDetailService;


	/**
	 * 商品詳細画面を出力します.
	 * 
	 * @param id    商品ID
	 * @param model リクエストスコープ
	 * @return      商品詳細画面
	 */
	@RequestMapping("")
	public String detail(String id, Model model) {
		Item item = showItemDetailService.findById(id);
		model.addAttribute("item", item);
		return "detail";
	}


}
