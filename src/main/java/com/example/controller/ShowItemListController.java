package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemListService;

/**
 * 商品一覧画面を操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/")
public class ShowItemListController {

	@Autowired
	private ShowItemListService showItemListService;

	@Autowired
	private HttpSession session;

	
	/**
	 * 商品一覧画面を出力します.
	 * 
	 * @param arrow カーソル
	 * @param model リクエストパラメータ
	 * @return      商品一覧画面
	 */
	@RequestMapping("")
	public String index(Integer arrow, Model model) {

		if (arrow == null || arrow == 0) {
			arrow = 0;
		}
		
		List<Item> itemList = showItemListService.findByPage(arrow);
		model.addAttribute("itemList", itemList);
		model.addAttribute("arrow", arrow);
		Integer totalPages = showItemListService.countPage();
		model.addAttribute("totalPages", totalPages);

		return "list";
	}

}