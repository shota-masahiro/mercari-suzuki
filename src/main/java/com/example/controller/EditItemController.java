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

import com.example.domain.Item;
import com.example.form.AddEditItemForm;
import com.example.service.EditItemService;
import com.example.service.ShowItemDetailService;

/**
 * 商品編集画面を操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/edit")
public class EditItemController {

	@Autowired
	private ShowItemDetailService showItemDetailService;

	@Autowired
	private EditItemService editItemService;

	@ModelAttribute
	private AddEditItemForm setUpAddEditItemForm() {
		return new AddEditItemForm();
	}

	/**
	 * 商品編集画面を出力します.
	 * 
	 * @param id    商品ID
	 * @param model リクエストスコープ
	 * @return      商品編集画面
	 */
	@RequestMapping("")
	public String toEdit(String id, Model model, String errorMessage) {

		if (errorMessage != null) {
			model.addAttribute("errorMessage", errorMessage);
		}

		Item item = showItemDetailService.findById(id);
		model.addAttribute("item", item);
		Map<String, Integer> conditionMap = new LinkedHashMap<>();
		for (int i = 1; i <= 3; i++) {
			conditionMap.put("condition" + i, i);
		}
		model.addAttribute("conditionMap", conditionMap);
		return "edit";
	}


	
	/**
	 * 更新処理をします.
	 * 
	 * @param form   リクエストパラメータ
	 * @param result エラーチェック
	 * @param model  リクエストスコープ
	 * @return
	 */
	@RequestMapping("/editItem")
	public String edit(
			@Validated AddEditItemForm form,
			BindingResult result,
			Model model) {

		String errorMessage = null;
		if ("---".equals(form.getLargeCategory()) || "".equals(form.getMediumCategory()) || "".equals(form.getSmallCategory())) {
			errorMessage = "カテゴリーを選択してください";
		}

		if (result.hasErrors()) {
			return toEdit(form.getItemId(), model, errorMessage);
		}

		editItemService.update(form);
		return "redirect:/";
	}

}