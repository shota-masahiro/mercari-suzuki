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
			String largeCategory, String mediumCategory, String smallCategory,
			Integer categoryId, String categoryName, CategorySearchForm form,
			String itemName) {

		//プルダウンset処理
		Integer checkCategory = (Integer) session.getAttribute("checkCategory");
		System.out.println("checkCategory:"+checkCategory);
//		if (checkCategory == null) {
			setCategory();
//		}

		if (form.getItemNameForm() != null && !"".equals(form.getItemNameForm())) {
			itemName = form.getItemNameForm();
		}

		//formで受け取ったカテゴリー値を格納する処理
		if (form.getLargeCategoryForm() != null || !"".equals(form.getLargeCategoryForm()) ) {
			if (!"---".equals(form.getLargeCategoryForm())) {
				largeCategory = form.getLargeCategoryForm();
			}

			if (form.getMediumCategoryForm() != null && !"".equals(form.getMediumCategoryForm()) && !"---".equals(form.getMediumCategoryForm())) {
				mediumCategory = form.getMediumCategoryForm();
			}

			if (form.getSmallCategoryForm() != null && !"".equals(form.getSmallCategoryForm()) && !"---".equals(form.getSmallCategoryForm())) {
				smallCategory = form.getSmallCategoryForm();
			}
		}

		if (form.getBrandForm() != null && !"".equals(form.getBrandForm())) {
			brand = form.getBrandForm();
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
		model.addAttribute("itemName", itemName);


		//メソッド化できる
		Integer totalPages;
		String category = null;
		if (brand != null) {
			totalPages = showItemListService.countPageBrand(brand);

		} else if (categorys[0] != null) {
			totalPages = showItemListService.countPageLarge(categorys[0]);
			categoryId = 0;
			category = categorys[0];
			if (categorys[1] != null) {

				try {
					Integer parentId = Integer.parseInt(categorys[1]);
					totalPages = showItemListService.countPageMediumInteger(parentId);
					categoryId = 1;
					Item item = showItemListService.findByCategoryMediumInteger2(parentId);
					category = item.getMediumCategory();
				} catch (Exception e) {
					totalPages = showItemListService.countPageMedium(categorys[1]);
					categoryId = 1;
					category = categorys[1];
				}

			}
			if (categorys[2] != null) {
				totalPages = showItemListService.countPageSmall(categorys[2]);
				categoryId = 2;
				category = categorys[2];
			}
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
			totalPages = showItemListService.countPageName(itemName);
		}
		if (itemName != null && brand != null) {
			totalPages = showItemListService.countPageNameBrand(itemName, brand);
		}

		if (itemName != null && categorys[2] != null && brand != null) {
			System.out.println("商品名と大中小カテゴリとブランド名のカウントページ数");
			totalPages = showItemListService.countPageNameSmallBrand(itemName, categorys[2], brand);
			categoryId = 2;
			category = categorys[2];
			System.out.println("totalPages:" + totalPages);
		} else if (itemName != null && categorys[1] != null && brand != null) {
			System.out.println("商品名と大中カテゴリとブランド名のカウントページ数");
			try {
				totalPages = showItemListService.countPageNameMediumBrandInteger(itemName, Integer.parseInt(categorys[1]), brand);
				categoryId = 1;
				Item item = showItemListService.findByCategoryMediumInteger2(Integer.parseInt(categorys[1]));
				category = item.getMediumCategory();
			} catch (Exception e) {
				totalPages = showItemListService.countPageNameMediumBrand(itemName, categorys[1], brand);
				categoryId = 1;
				category = categorys[1];
			}
		} else if (itemName != null && categorys[0] != null && brand != null) {
			System.out.println("商品名と大カテゴリとブランド名のカウントページ数");
			totalPages = showItemListService.countPageNameLargeBrand(itemName, categorys[0], brand);
			categoryId = 0;
			category = categorys[0];
		} else if (itemName != null && categorys[2] != null) {
			System.out.println("商品名と大中小カテゴリのカウントページ数");
			totalPages = showItemListService.countPageNameLarge(itemName, categorys[0]);
			categoryId = 2;
			category = categorys[2];
		} else if (itemName != null && categorys[1] != null) {
			System.out.println("商品名と大中カテゴリのカウントページ数");
			try {
				totalPages = showItemListService.countPageNameMediumInteger(itemName, Integer.parseInt(categorys[1]));
				categoryId = 1;
				Item item = showItemListService.findByCategoryMediumInteger2(Integer.parseInt(categorys[1]));
				category = item.getMediumCategory();
			} catch (Exception e) {
				totalPages = showItemListService.countPageNameMedium(itemName, categorys[1]);
				categoryId = 1;
				category = categorys[1];
			}

		} else if (itemName != null && categorys[0] != null) {
			System.out.println("商品名と大カテゴリのカウントページ数");
			totalPages = showItemListService.countPageNameLarge(itemName, categorys[0]);
			categoryId = 0;
			category = categorys[0];
		}
		System.out.println("totalPages:"+ totalPages);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("categoryName", category);

		return "list";
	}

	/**
	 * プルダウンで使用する値をsetします.
	 */
	public void setCategory() {
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