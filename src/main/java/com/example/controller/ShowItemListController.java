package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.CategoryName;
import com.example.domain.CategorySearchForm;
import com.example.domain.Item;
import com.example.service.CountPageService;
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

	@ModelAttribute
	public CategorySearchForm setUpCategorySearchForm() {
		return new CategorySearchForm();
	}


	/**
	 * 商品一覧画面を出力します.
	 * 
	 * @param arrow カーソル
	 * @param model リクエストパラメータ
	 * @return      商品一覧画面
	 */
	@SuppressWarnings("unused")
	@RequestMapping("")
	public String index(Integer arrow, String brand, Model model,
			Integer largeCategory, Integer mediumCategory, Integer smallCategory,
			Integer[] categoryIds, CategorySearchForm form, String itemName) {

		Integer checkCategory = (Integer) session.getAttribute("checkCategory");
		if (checkCategory == null) {
			setCategory();
		}

		if (form.getItemNameForm() != null && !"".equals(form.getItemNameForm())) {
			itemName = form.getItemNameForm();
		}

		if (form.getLargeCategoryForm() != null && !"".equals(form.getLargeCategoryForm()) && !"---".equals(form.getLargeCategoryForm())) { 
			largeCategory = form.getIntLargeCategoryForm();

			if (form.getMediumCategoryForm() != null && !"".equals(form.getMediumCategoryForm()) && !"---".equals(form.getMediumCategoryForm())) {
				mediumCategory = form.getIntCategoryForm();
			}

			if (form.getSmallCategoryForm() != null && !"".equals(form.getSmallCategoryForm()) && !"---".equals(form.getSmallCategoryForm())) {
				smallCategory = form.getIntSmallCategoryForm();
			}
		}

		if (categoryIds != null) {

		} else if (largeCategory != null || mediumCategory != null || smallCategory != null) {
			categoryIds = new Integer[]{largeCategory, mediumCategory, smallCategory};
		} else {
			categoryIds = new Integer[]{null, null, null};
		}

		if (form.getBrandForm() != null && !"".equals(form.getBrandForm())) {
			brand = form.getBrandForm();
		}

		if (arrow == null) {
			arrow = 0;
		}
		if ("".equals(brand)) {
			brand = null;
		}
		if ("".equals(itemName)) {
			itemName = null;
		}

		List<Item> itemList = showItemListService.findByPage(arrow, brand, categoryIds, itemName);
		model.addAttribute("itemList", itemList);
		model.addAttribute("arrow", arrow);
		model.addAttribute("brand", brand);
		model.addAttribute("itemName", itemName);


		Integer totalPages = showItemListService.getCountPage(itemName, categoryIds, brand);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("categoryIds", categoryIds);

		return "list";
	}

	/** プルダウンで使用する値をsetします. */
	private void setCategory() {
		List<CategoryName> categoryLargeNameList = showItemListService.categoryLargeText();
		session.setAttribute("categoryLargeNameList", categoryLargeNameList);

		List<CategoryName> categoryMediumNameList = showItemListService.categoryMediumText();
		session.setAttribute("categoryMediumNameList", categoryMediumNameList);

		List<CategoryName> categorySmallNameList = showItemListService.categorySmallText();
		session.setAttribute("categorySmallNameList", categorySmallNameList);

		session.setAttribute("checkCategory", -99);
	}

}