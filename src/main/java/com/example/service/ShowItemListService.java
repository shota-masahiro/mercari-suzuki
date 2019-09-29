package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.CategoryName;
import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品一覧情報を操作するサービス.
 * 
 * @author shota.suzuki
 *
 */
@Service
public class ShowItemListService {

	@Autowired
	private ItemRepository itemRepository;


	/**
	 * 検索処理をします.
	 * 
	 * @param arrow カーソル
	 * @return      商品情報一覧
	 */
	public List<Item> findByPage(Integer arrow, String brand, Integer[] categorys, String itemName) {

		if (arrow != 0) {// カーソル移動処理
			arrow = moveArrow(arrow);
		}

		if (itemName != null && categorys[2] != null && brand != null) {// 商品名 + 大中小カテゴリ + ブランド名
			return itemRepository.findByNameCategorySmallBrand(arrow, ("%"+itemName+"%"), categorys[2], ("%"+brand+"%"));
		}

		if (itemName != null && categorys[1] != null && brand != null) {// 商品名 + 大中カテゴリ + ブランド名
			return itemRepository.findByNameCategoryMediumBrand(arrow, ("%"+itemName+"%"), categorys[1], ("%"+brand+"%"));
		}

		if (itemName != null && categorys[0] != null && brand != null) {// 商品名 + 大カテゴリ + ブランド名
			return itemRepository.findByNameCategoryLargeBrand(arrow, ("%"+itemName+"%"), categorys[0], ("%"+brand+"%"));
		}

		if (itemName != null && categorys[2] != null) {// 商品名 + 大中小カテゴリ
			return itemRepository.findByNameCategorySmall(arrow, ("%"+itemName+"%"), categorys[2]);
		}

		if (itemName != null && categorys[1] != null) {// 商品名 + 大中カテゴリ
			return itemRepository.findByNameCategoryMedium(arrow, ("%"+itemName+"%"), categorys[1]);
		}

		if (itemName != null && categorys[0] != null) {// 商品名 + 大カテゴリ
			return itemRepository.findByNameCategoryLarge(arrow, ("%"+itemName+"%"), categorys[0]);
		}

		if (categorys[2] != null && brand != null) {// 大中小カテゴリ + ブランド名
			return itemRepository.findByCategorySmallBrand(arrow, categorys[2], ("%"+brand+"%"));
		}

		if (categorys[1] != null && brand != null) {// 大中カテゴリ + ブランド名
			return itemRepository.findByCategoryMediumBrand(arrow, categorys[1], ("%"+brand+"%"));
		}

		if (categorys[0] != null && brand != null) {// 大カテゴリ + ブランド名
			return itemRepository.findByCategoryLargeBrand(arrow, categorys[0], ("%"+brand+"%"));
		}

		if (brand != null && itemName != null) {// 商品名 + ブランド名
			return itemRepository.findByNameBrandPage(arrow, ("%"+itemName+"%"), ("%"+ brand +"%"));
		}

		if (categorys[2] != null) {// 大中小カテゴリのみ
			return itemRepository.findByCategorySmall(categorys[2], arrow);
		}

		if (categorys[1] != null) {// 大中カテゴリのみ
			return itemRepository.findByCategoryMedium(categorys[1], arrow);
		}

		if (categorys[0] != null) {// 大カテゴリのみ
			return itemRepository.findByCategoryLarge(categorys[0], arrow);
		}

		if (brand != null) {// ブランド名のみ
			return itemRepository.findByBrand(arrow, ("%"+brand+"%"));
		}

		if (itemName != null) {// 商品名のみ
			return itemRepository.findByNamePage(("%"+itemName+"%"), arrow);
		}

		return itemRepository.findByPage(arrow);
	}


	public Integer countPage() {// 全件検索のページ数
		return itemRepository.countPage();
	}
	public Integer countPageName(String itemName) {// 商品のあいまい検索
		int page = itemRepository.countPageName(("%"+itemName+"%"));
		page = countPlasPage(page);
		return page;
	}
	public Integer countPageBrand(String brand) {// ブランド名のあいまい検索
		int page = itemRepository.countPageBrand(("%"+brand+"%"));
		page = countPlasPage(page);
		return page;
	}
	public Integer countPageNameBrand(String itemName, String brand) {// 商品名とブランド名のあいまい検索
		int page = itemRepository.countPageNameBrand(("%"+itemName+"%"), ("%"+ brand +"%"));
		page = countPlasPage(page);
		return page;
	}


	public Integer countPageSmall(Integer small) {// 小カテゴリのみの検索ページ数
		int page = itemRepository.countPageSmall(small);
		page = countPlasPage(page);
		return page;
	}
	public Integer countPageMedium(Integer categoryId) {// 中カテゴリのみの検索ページ数
		int page = itemRepository.countPageMedium(categoryId);
		page = countPlasPage(page);
		return page;
	}
	public Integer countPageLarge(Integer categoryId) {// 大カテゴリのみの検索ページ数
		int page = itemRepository.countPageLarge(categoryId);
		page = countPlasPage(page);
		return page;
	}


	public Integer countPageNameSmall(String itemName, Integer categoryId) {// 商品名と小カテゴリで検索
		int page = itemRepository.countPageNameSmall(("%"+itemName+"%"), categoryId);
		page = countPlasPage(page);
		return page;
	}
	public Integer countPageNameMedium(String itemName, Integer categoryId) {// 商品名と中カテゴリで検索
		int page = itemRepository.countPageNameMedium(("%"+itemName+"%"), categoryId);
		page = countPlasPage(page);
		return page;
	}
	public Integer countPageNameLarge(String itemName, Integer categoryId) {// 商品名と大カテゴリで検索
		int page = itemRepository.countPageNameLarge(("%"+itemName+"%"), categoryId);
		page = countPlasPage(page);
		return page;
	}


	public Integer countPageSmallBrand(Integer categoryId, String brand) {// 小カテゴリとブランド名で検索
		int page = itemRepository.countPageSmallBrand(categoryId, ("%"+brand+"%"));
		page = countPlasPage(page);
		return page;
	}
	public Integer countPageMediumBrand(Integer categoryId, String brand) {// 中カテゴリとブランド名で検索
		int page = itemRepository.countPageMediumBrand(categoryId, ("%"+brand+"%"));
		page = countPlasPage(page);
		return page;
	}
	public Integer countPageLargeBrand(Integer categoryId, String brand) {// 大カテゴリとブランド名で検索
		int page = itemRepository.countPageLargeBrand(categoryId, ("%"+brand+"%"));
		page = countPlasPage(page);
		return page;
	}


	public Integer countPageNameSmallBrand(String itemName, Integer categoryId, String brand) {// 商品名と小カテゴリとブランド名で検索
		int page = itemRepository.countPageNameSmallBrand(("%"+itemName+"%"), categoryId, ("%"+brand+"%"));
		page = countPlasPage(page);
		return page;
	}
	public Integer countPageNameMediumBrand(String itemName, Integer categoryId, String brand) {// 商品名と中カテゴリとブランド名で検索
		int page = itemRepository.countPageNameMediumBrand(("%"+itemName+"%"), categoryId, ("%"+brand+"%"));
		page = countPlasPage(page);
		return page;
	}
	public Integer countPageNameLargeBrand(String itemName, Integer categoryId, String brand) {// 商品名と大カテゴリとブランド名で検索
		int page = itemRepository.countPageNameLargeBrand(("%"+itemName+"%"), categoryId, ("%"+brand+"%"));
		page = countPlasPage(page);
		return page;
	}


	public List<CategoryName> categoryLargeText() {
		return itemRepository.categoryLargeText();
	}
	public List<CategoryName> categoryMediumText() {
		return itemRepository.categoryMediumText();
	}
	public List<CategoryName> categorySmallText() {
		return itemRepository.categorySmallText();
	}


	private int countPlasPage(int page) {//1ページ＋するメソッド
		if (page == 1) {
			page++;
		}
		return page;
	}

	private Integer moveArrow(Integer arrow) {// カーソルの移動処理
		int move = arrow * 30;
		return arrow = move;
	}

}
