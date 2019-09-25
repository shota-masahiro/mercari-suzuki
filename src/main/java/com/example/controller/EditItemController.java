package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品編集画面を操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/edit")
public class EditItemController {
	
	
	@RequestMapping("")
	public String toEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "edit";
	}
	
}