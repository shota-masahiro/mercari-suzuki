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
			Integer categoryId, String[] categoryIds, CategorySearchForm form,
			String itemName) {

		Integer checkCategory = (Integer) session.getAttribute("checkCategory");
		//		if (checkCategory == null) {}
		setCategory();

		if (form.getItemNameForm() != null && !"".equals(form.getItemNameForm())) {
			itemName = form.getItemNameForm();
		}

		if (form.getLargeCategoryForm() != null && !"".equals(form.getLargeCategoryForm()) ) { 
			if (!"---".equals(form.getLargeCategoryForm())) {
				largeCategory = form.getIntLargeCategoryForm();
			}

			if (form.getMediumCategoryForm() != null && !"".equals(form.getMediumCategoryForm()) && !"---".equals(form.getMediumCategoryForm())) {
				mediumCategory = form.getIntCategoryForm();
			}

			if (form.getSmallCategoryForm() != null && !"".equals(form.getSmallCategoryForm()) && !"---".equals(form.getSmallCategoryForm())) {
				smallCategory = form.getIntSmallCategoryForm();
			}
		}

		if (form.getBrandForm() != null && !"".equals(form.getBrandForm())) {
			brand = form.getBrandForm();
		}

		Integer[] categorys = {largeCategory, mediumCategory, smallCategory};

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
		model.addAttribute("itemName", itemName); // itemNameはセレクトボックスに連動している商品名の検索の値


		Integer totalPages = null;
		boolean key = true;

		//		if (itemName != null && categorys[2] != null && brand != null && key) { //商品名 + 大中小カテゴリ + ブランド名
		//			totalPages = showItemListService.countPageNameSmallBrand(itemName, categorys[2], brand);
		//			key = false;
		//		}

		//		if (itemName != null && categorys[1] != null && brand != null && key) { //商品名 + 大中カテゴリ + ブランド名
		//			totalPages = showItemListService.countPageNameMediumBrandInteger(itemName, categorys[1], brand);
		//			key = false;
		//		}

		//		if (itemName != null && categorys[0] != null && brand != null && key) { //商品名 + 大カテゴリ + ブランド名
		//			totalPages = showItemListService.countPageNameLargeBrand(itemName, categorys[0], brand);
		//			key = false;
		//		}

		if (itemName != null && categorys[2] != null && key) { //商品名 + 大中小カテゴリ
			totalPages = showItemListService.countPageNameSmall(itemName, categorys[2]);
			key = false;
		}

		if (itemName != null && categorys[1] != null && key) { //商品名 + 大中カテゴリ
			totalPages = showItemListService.countPageNameMedium(itemName, categorys[1]);
			key = false;
		}

		if (itemName != null && categorys[0] != null && key) { //商品名 + 大カテゴリ
			totalPages = showItemListService.countPageNameLarge(itemName, categorys[0]);
			key = false;
		}

		//		if (categorys[2] != null && brand != null && key) { //大中小カテゴリ + ブランド名
		//			totalPages = showItemListService.countPageSmallBrand(brand, categorys[2]);
		//			key = false;
		//		}

		//		if (categorys[1] != null && brand != null && key) { //大中カテゴリ + ブランド名
		//			totalPages = showItemListService.countPageMediumBrandInteger(brand, parentId);
		//			key = false;
		//		}

		//		if (categorys[0] != null && brand != null && key) { //大カテゴリ + ブランド名
		//			totalPages = showItemListService.countPageLargeBrand(brand, categorys[0]);
		//			key = false;
		//		}

		if (itemName != null && brand != null && key) { //商品名 + ブランド名 ok 
			totalPages = showItemListService.countPageNameBrand(itemName, brand);
			key = false;
		}

		if (categorys[2] != null && key) { //大中小カテゴリのみ ok 
			totalPages = showItemListService.countPageSmall(categorys[2]);
			key = false;
		}
		
		if (categorys[1] != null && key) { //大中カテゴリのみ ok
			totalPages = showItemListService.countPageMedium(categorys[1]);
			key = false;
		}
		
		if (categorys[0] != null && key) { //大カテゴリのみ ok
			totalPages = showItemListService.countPageLarge(categorys[0]);
			key = false;
		}

		if (brand != null && key) { //ブランド名のみ ok
			totalPages = showItemListService.countPageBrand(brand);
			key = false;
		}
		
		if (itemName != null && key) { //商品名のみ ok
			totalPages = showItemListService.countPageName(itemName);
			key = false;
		}

		if (key) { //全件検索 ok
			totalPages = showItemListService.countPage();
		}

		model.addAttribute("totalPages", totalPages);
		model.addAttribute("categoryIds", categorys);

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