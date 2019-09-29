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


	public Integer getCountPage(String itemName, Integer[] categorys, String brand) {

		if (itemName != null && categorys[2] != null && brand != null) {// 商品名 + 大中小カテゴリ + ブランド名
			int page = itemRepository.countPageNameSmallBrand(("%"+itemName+"%"), categorys[2], ("%"+brand+"%"));
			return countPlasPage(page);
		}

		if (itemName != null && categorys[1] != null && brand != null) {// 商品名 + 大中カテゴリ + ブランド名
			int page = itemRepository.countPageNameMediumBrand(("%"+itemName+"%"), categorys[1], ("%"+brand+"%"));
			return countPlasPage(page);
		}

		if (itemName != null && categorys[0] != null && brand != null) {// 商品名 + 大カテゴリ + ブランド名
			int page = itemRepository.countPageNameLargeBrand(("%"+itemName+"%"), categorys[0], ("%"+brand+"%"));
			return countPlasPage(page);
		}

		if (itemName != null && categorys[2] != null) {// 商品名 + 大中小カテゴリ
			int page = itemRepository.countPageNameSmall(("%"+itemName+"%"), categorys[2]);
			return countPlasPage(page);
		}

		if (itemName != null && categorys[1] != null) {// 商品名 + 大中カテゴリ
			int page = itemRepository.countPageNameMedium(("%"+itemName+"%"), categorys[1]);
			return countPlasPage(page);
		}

		if (itemName != null && categorys[0] != null) {// 商品名 + 大カテゴリ
			int page = itemRepository.countPageNameLarge(("%"+itemName+"%"), categorys[0]);
			return countPlasPage(page);
		}

		if (categorys[2] != null && brand != null) {// 大中小カテゴリ + ブランド名
			int page = itemRepository.countPageSmallBrand(categorys[2], ("%"+brand+"%"));
			return countPlasPage(page);
		}

		if (categorys[1] != null && brand != null) {// 大中カテゴリ + ブランド名
			int page = itemRepository.countPageMediumBrand(categorys[1], ("%"+brand+"%"));
			return countPlasPage(page);
		}

		if (categorys[0] != null && brand != null) {// 大カテゴリ + ブランド名
			int page = itemRepository.countPageLargeBrand(categorys[0], ("%"+brand+"%"));
			return countPlasPage(page);
		}

		if (itemName != null && brand != null) {// 商品名 + ブランド名 
			int page = itemRepository.countPageNameBrand(("%"+itemName+"%"), ("%"+ brand +"%"));
			return countPlasPage(page);
		}

		if (categorys[2] != null) {// 大中小カテゴリのみ 
			int page = itemRepository.countPageSmall(categorys[2]);
			return countPlasPage(page);
		}

		if (categorys[1] != null) {// 大中カテゴリのみ
			int page = itemRepository.countPageMedium(categorys[1]);
			return countPlasPage(page);
		}

		if (categorys[0] != null) {// 大カテゴリのみ
			int page = itemRepository.countPageLarge(categorys[0]);
			return countPlasPage(page);
		}

		if (brand != null) {// ブランド名のみ
			int page = itemRepository.countPageBrand(("%"+brand+"%"));
			return countPlasPage(page);
		}

		if (itemName != null) {// 商品名のみ
			int page = itemRepository.countPageName(("%"+itemName+"%"));
			return countPlasPage(page);
		}

		return itemRepository.countPage();// 全件検索のページ数
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