package com.example.controller;

import java.util.LinkedList;
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
			String largeCategory, String mediumCategory, String smallCategory,
			Integer categoryId, String categoryName, CategorySearchForm form,
			String itemName) {

		System.out.println("categoryId:" + categoryId);

		System.out.println("categorySearchForm:" + form);
		System.out.println("categorySearchForm■:" + form == null);
		System.out.println("categorySearchForm□:" + form != null);


		if (form.getItemName() != null || !"".equals(form.getItemName())) {
			itemName = form.getItemName();
		}

		if (form.getLargeCategory() != null || !"".equals(form.getLargeCategory()) ) {
			//カテゴリーが選択されていた場合
		}

		if (form.getBrand() != null || !"".equals(form.getBrand())) {
			brand = form.getBrand();
		}


		String[] categorys = {largeCategory, mediumCategory, smallCategory};
		if (categoryId != null) {
			if (categoryId == 0) {
				categorys[0] = categoryName;
			} else if (categoryId == 1) {
				categorys[1] = categoryName;
			} else if (categoryId == 2) {
				categorys[2] = categoryName;
			}
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
		List<Item> itemList = showItemListService.findByPage(arrow, brand, categorys, itemName);
		model.addAttribute("itemList", itemList);
		model.addAttribute("arrow", arrow);
		model.addAttribute("brand", brand);

		//メソッド化できる
		Integer totalPages;
		String category = null;
		if (brand != null) {
			totalPages = showItemListService.countPageBrand(brand);
		} else if (categorys[0] != null) {
			totalPages = showItemListService.countPageLarge(categorys[0]);
			categoryId = 0;
			category = categorys[0];
		} else if (categorys[1] != null) {
			totalPages = showItemListService.countPageMedium(categorys[1]);
			categoryId = 1;
			category = categorys[1];
		} else if (categorys[2] != null) {
			totalPages = showItemListService.countPageSmall(categorys[2]);
			categoryId = 2;
			category = categorys[2];
		} else {
			totalPages = showItemListService.countPage();
		}
		if (itemName != null) {
			System.out.println("商品名検索の総ページ数を取得");
			totalPages = showItemListService.countPageName(itemName);
		}
		if (itemName != null && brand != null) {
			System.out.println("itemNameとbrandで検索した総ページ数を取得");
			totalPages = showItemListService.countPageNameBrand(itemName, brand);
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("categoryName", category);

		model.addAttribute("itemName", itemName);

		//ここはsessionでメソッドで切り出し
		List<CategoryName> categoryLargeNameList = showItemListService.categoryLargeText();
		List<String> categoryLargeList = new LinkedList<>();
		model.addAttribute("categoryLargeNameList", categoryLargeNameList);


		List<CategoryName> categoryMediumNameList = showItemListService.categoryMediumText();
		List<String> categoryMediumList = new LinkedList<>();
		model.addAttribute("categoryMediumNameList", categoryMediumNameList);


		List<CategoryName> categorySmallNameList = showItemListService.categorySmallText();
		List<String> categorySmallList = new LinkedList<>();
		model.addAttribute("categorySmallNameList", categorySmallNameList);

		return "list";
	}

}