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
			Integer categoryId, String categoryName, CategorySearchForm form,
			String itemName) {

		//プルダウンset処理
		Integer checkCategory = (Integer) session.getAttribute("checkCategory");
		//		if (checkCategory == null) {}
		setCategory();

		if (form.getItemNameForm() != null && !"".equals(form.getItemNameForm())) {
			itemName = form.getItemNameForm();
		}

		//formで受け取ったカテゴリー値を格納する処理
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
		if (categoryId != null) {
			if (categoryId == 0) {
				categorys[0] = largeCategory;
			} else if (categoryId == 1) {
				categorys[1] = mediumCategory;
			} else if (categoryId == 2) {
				categorys[2] = smallCategory;
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
		model.addAttribute("itemName", itemName);

		Integer totalPages = null;
		Integer category = null;
		boolean key = true;

		//商品名 + 大中小カテゴリ + ブランド名
//		if (itemName != null && categorys[2] != null && brand != null && key) {
//			totalPages = showItemListService.countPageNameSmallBrand(itemName, categorys[2], brand);
//			categoryId = 2;
//			category = categorys[2];
//			key = false;
//		}

		//商品名 + 大中カテゴリ + ブランド名
//		if (itemName != null && categorys[1] != null && brand != null && key) {
//				totalPages = showItemListService.countPageNameMediumBrandInteger(itemName, Integer.parseInt(categorys[1]), brand);
//				categoryId = 1;
//				category = categorys[1];
//				key = false;
//		}

		//商品名 + 大カテゴリ + ブランド名
//		if (itemName != null && categorys[0] != null && brand != null && key) {
//			totalPages = showItemListService.countPageNameLargeBrand(itemName, categorys[0], brand);
//			categoryId = 0;
//			category = categorys[0];
//			key = false;
//		}

		//商品名 + 大中小カテゴリ
//		if (itemName != null && categorys[2] != null && key) {
//			totalPages = showItemListService.countPageNameLarge(itemName, categorys[0]);
//			categoryId = 2;
//			category = categorys[2];
//			key = false;
//		}

		//商品名 + 大中カテゴリ
//		if (itemName != null && categorys[1] != null && key) {
//				totalPages = showItemListService.countPageNameMediumInteger(itemName, Integer.parseInt(categorys[1]));
//				categoryId = 1;
//				category = categorys[0];
//				key = false;
//		}

		//商品名 + 大カテゴリ
//		if (itemName != null && categorys[0] != null && key) {
//			totalPages = showItemListService.countPageNameLarge(itemName, categorys[0]);
//			categoryId = 0;
//			category = categorys[0];
//			key = false;
//		}

		//大中小カテゴリ + ブランド名
//		if (categorys[2] != null && brand != null && key) {
//			totalPages = showItemListService.countPageSmallBrand(brand, categorys[2]);
//			categoryId = 2;
//			category = categorys[2];
//			key = false;
//		}

		//大中カテゴリ + ブランド名
//		if (categorys[1] != null && brand != null && key) {
//				totalPages = showItemListService.countPageMediumBrandInteger(brand, parentId);
//				categoryId = 1;
//		category = categorys[1];
//				key = false;
//		}

		//大カテゴリ + ブランド名
//		if (categorys[0] != null && brand != null && key) {
//			totalPages = showItemListService.countPageLargeBrand(brand, categorys[0]);
//			categoryId = 0;
//			category = categorys[0];
//			key = false;
//		}

		//商品名 + ブランド名 ok 
		if (itemName != null && brand != null && key) {
			totalPages = showItemListService.countPageNameBrand(itemName, brand);
			key = false;
		}

		//lengthで判定して中身が空ではなかったらサービスに渡す
		//サービスでは1つのメソッドで配列を受け取り、そこからリポジトリの呼出しを分岐させる,

		//大中小カテゴリのみ ok 
		if (categorys[2] != null && key) {
			totalPages = showItemListService.countPageSmall(categorys[2]);
			categoryId = 2;
			category = categorys[2];
			key = false;
		}

		//大中カテゴリのみ ok
		if (categorys[1] != null && key) {
				totalPages = showItemListService.countPageMedium(categorys[1]);
				categoryId = 1;
				category = categorys[1];
				key = false;
		}

		//大カテゴリのみ ok
		if (categorys[0] != null && key) {
			totalPages = showItemListService.countPageLarge(categorys[0]);
			categoryId = 0;
			category = categorys[0];
			key = false;
		}

		//ブランド名のみ ok
		if (brand != null && key) {
			totalPages = showItemListService.countPageBrand(brand);
			key = false;
		}

		//商品名のみ ok
		if (itemName != null && key) {
			totalPages = showItemListService.countPageName(itemName);
			key = false;
		}

		//全件検索
		if (key) {
			totalPages = showItemListService.countPage();
		}

		model.addAttribute("totalPages", totalPages);
		model.addAttribute("categoryId", categoryId);
//		model.addAttribute("categoryName", category);

		return "list";
	}

	/**
	 * プルダウンで使用する値をsetします.
	 */
	private void setCategory() {
		List<CategoryName> categoryLargeNameList = showItemListService.categoryLargeText();
		session.setAttribute("categoryLargeNameList", categoryLargeNameList);

		List<CategoryName> categoryMediumNameList = showItemListService.categoryMediumText();
		session.setAttribute("categoryMediumNameList", categoryMediumNameList);

		List<CategoryName> categorySmallNameList = showItemListService.categorySmallText();
		session.setAttribute("categorySmallNameList", categorySmallNameList);

		//確認用
		session.setAttribute("checkCategory", -99);
	}

}