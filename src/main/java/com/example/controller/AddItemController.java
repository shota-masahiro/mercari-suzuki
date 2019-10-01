package com.example.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.AddEditItemForm;
import com.example.service.AddItemService;

/**
 * 商品登録画面を操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/add")
public class AddItemController {

	@Autowired
	private AddItemService addItemService;

	@ModelAttribute
	public AddEditItemForm setUpAddEditForm() {
		return new AddEditItemForm();
	}

	/**
	 * 商品登録画面を出力します.
	 * 
	 * @return 商品登録画面
	 */
	@RequestMapping("")
	public String toAdd(Model model, String errorMessage) {
		
		if (errorMessage != null) {
			model.addAttribute("errorMessage", errorMessage);
		}

		Map<String, Integer> conditionMap = new LinkedHashMap<>();
		for (int i = 1; i <= 3; i++) {
			conditionMap.put("condition" + i, i);
		}
		model.addAttribute("conditionMap", conditionMap);

		return "add";
	}



	/**
	 * 商品追加処理をします.
	 * 
	 * @param form   リクエストパラメータ
	 * @param result エラーチェック
	 * @param model  リクエストスコープ
	 * @return       商品一覧画面
	 */
	@RequestMapping("/addItem")
	public String add(
			@Validated AddEditItemForm form,
			BindingResult result,
			Model model) {

		String errorMessage = null;
		if ("---".equals(form.getLargeCategory()) || "---".equals(form.getMediumCategory()) || "---".equals(form.getSmallCategory())) {
			errorMessage = "カテゴリーを選択してください";
		}

		if (result.hasErrors() || errorMessage != null) {
			return toAdd(model, errorMessage);
		}
		
		addItemService.insert(form);
		return "redirect:/";
	}

}
















