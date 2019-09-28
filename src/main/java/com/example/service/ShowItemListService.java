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

		//カーソル移動処理
		if (arrow != 0) {
			arrow = moveArrow(arrow);
		}

		//商品名 + 大中小カテゴリ + ブランド名
		//		if (itemName != null && categorys[2] != null && brand != null) {
		//			return itemRepository.findByNameCategorySmallBrand(arrow, ("%"+itemName+"%"), categorys[2], ("%"+brand+"%"));
		//		}

		//商品名 + 大中カテゴリ + ブランド名
		//		if (itemName != null && categorys[1] != null && brand != null) {
		//			try {
		//				return itemRepository.findByNameCategoryMediumBrandInteger(arrow, ("%"+itemName+"%"), Integer.parseInt(categorys[1]), ("%"+brand+"%"));
		//			} catch (Exception e) {
		//				return itemRepository.findByNameCategoryMediumBrand(arrow, ("%"+itemName+"%"), categorys[1], ("%"+brand+"%"));
		//			}
		//		}

		//商品名 + 大カテゴリ + ブランド名
		//		if (itemName != null && categorys[0] != null && brand != null) {
		//			return itemRepository.findByNameCategoryLargeBrand(arrow, ("%"+itemName+"%"), categorys[0], ("%"+brand+"%"));
		//		}

		//商品名 + 大中小カテゴリ
		//		if (itemName != null && categorys[2] != null) {
		//			return itemRepository.findByNameCategorySmall(arrow, ("%"+itemName+"%"), categorys[2]);
		//		}

		//商品名 + 大中カテゴリ
		//		if (itemName != null && categorys[1] != null) {
		//			try {
		//				return itemRepository.findByNameCategoryMediumInteger(arrow, Integer.parseInt(categorys[1]), ("%"+itemName+"%"));
		//			} catch (Exception e) {
		//				return itemRepository.findByNameCategoryMedium(arrow, categorys[1], ("%"+itemName+"%"));
		//			}
		//		}

		//商品名 + 大カテゴリ
		//		if (itemName != null && categorys[0] != null) {
		//			return itemRepository.findByNameCategoryLarge(arrow, ("%"+itemName+"%"), categorys[0]);
		//		}

		//大中小カテゴリ + ブランド名
		//		if (categorys[2] != null && brand != null) {
		//			return itemRepository.findByCategorySmallBrand(arrow, ("%"+brand+"%"), categorys[2]);
		//		}

		//大中カテゴリ + ブランド名
		//		if (categorys[1] != null && brand != null) {
		//			System.out.println("処理開始");
		//			try {
		//				return itemRepository.findByCategoryMediumBrandInteger(arrow, Integer.parseInt(categorys[1]), ("%"+brand+"%"));
		//			} catch (Exception e) {
		//				return itemRepository.findByCategoryMediumBrand(arrow, ("%"+brand+"%"), categorys[1]);
		//			}
		//		}

		//大カテゴリ + ブランド名
		//		if (categorys[0] != null && brand != null) {
		//			return itemRepository.findByCategoryLargeBrand(arrow, ("%"+brand+"%"), categorys[0]);
		//		}

		//商品名 + ブランド名
		if (brand != null && itemName != null) {
			return itemRepository.findByNameBrandPage(("%"+itemName+"%"), ("%"+ brand +"%"), arrow);
		}

		//大中小カテゴリのみ
		if (categorys[2] != null) {
			return itemRepository.findByCategorySmall(categorys[2], arrow);
		}

		//大中カテゴリのみ
		if (categorys[1] != null) {
			return itemRepository.findByCategoryMedium(categorys[1], arrow);
		}

		//大カテゴリのみ
		if (categorys[0] != null) {
			return itemRepository.findByCategoryLarge(categorys[0], arrow);
		}

		//ブランド名のみ
		if (brand != null) {
			return itemRepository.findByBrand(arrow, ("%"+brand+"%"));
		}

		//商品名のみ
		if (itemName != null) {
			return itemRepository.findByNamePage(("%"+itemName+"%"), arrow);
		}

		return itemRepository.findByPage(arrow);
	}


	public Item findByCategoryMediumInteger2(Integer parentId) {
		return itemRepository.findByCategoryMediumIntegerSecond(parentId);
	}


	/**
	 * 総ページ数を取得します.
	 * 
	 * @return ページ数
	 */
	public Integer countPage() {
		return itemRepository.countPage();
	}


	/**
	 * 総ページ数を取得します.
	 * 
	 * @param brand ブランド
	 * @return      総ページ数
	 */
	public Integer countPageBrand(String brand) {
		int page = itemRepository.countPageBrand(("%"+brand+"%"));
		if (page == 0) {
			page = 1;
		}
		return page;
	}


	public Integer countPageLarge(Integer categoryId) {
		int page = itemRepository.countPageLarge(categoryId);
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	public Integer countPageMedium(Integer categoryId) {
		int page = itemRepository.countPageMedium(categoryId);
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	public Integer countPageSmall(Integer small) {
		int page = itemRepository.countPageSmall(small);
		if (page == 0) {
			page = 1;
		} else if (page == 1) {
			page++;
		}
		return page;
	}

	//商品のあいまい検索
	public Integer countPageName(String itemName) {
		int page = itemRepository.countPageName(("%"+itemName+"%"));
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	//商品名とブランド名のあいまい検索
	public Integer countPageNameBrand(String itemName, String brand) {
		int page = itemRepository.countPageNameBrand(("%"+itemName+"%"), ("%"+ brand +"%"));
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	//商品名と大カテゴリで検索
	public Integer countPageNameLarge(String itemName, String categoryNameLarge) {
		int page = itemRepository.countPageNameLarge(("%"+itemName+"%"), categoryNameLarge);
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	//商品名と中カテゴリで検索
	public Integer countPageNameMediumInteger(String itemName, Integer parentId) {
		int page = itemRepository.countPageNameMediumInteger(("%"+itemName+"%"), parentId);
		if (page == 0) {
			page = 1;
		}
		return page;
	}
	public Integer countPageNameMedium(String itemName, String categoryName) {
		int page = itemRepository.countPageNameMedium(("%"+itemName+"%"), categoryName);
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	//商品名と小カテゴリで検索
	public Integer countPageNameSmall(String itemName, String categoryNameLarge) {
		int page = itemRepository.countPageNameSmall(("%"+itemName+"%"), categoryNameLarge);
		if (page == 0) {
			page = 1;
		}
		return page;
	}


	//大カテゴリとブランド名で検索
	public Integer countPageLargeBrand(String brand, String categoryNameLarge) {
		int page = itemRepository.countPageLargeBrand(("%"+brand+"%"), categoryNameLarge);
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	//中カテゴリとブランド名で検索
	public Integer countPageMediumBrandInteger(String brand, Integer parentId) {
		int page = itemRepository.countPageMediumBrandInteger(("%"+brand+"%"), parentId);
		if (page == 0) {
			page = 1;
		}
		return page;
	}
	public Integer countPageMediumBrand(String brand, String categoryName) {
		int page = itemRepository.countPageMediumBrand(("%"+brand+"%"), categoryName);
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	//小カテゴリとブランド名で検索
	public Integer countPageSmallBrand(String brand, String categoryNameLarge) {
		int page = itemRepository.countPageSmallBrand(("%"+brand+"%"), categoryNameLarge);
		if (page == 0) {
			page = 1;
		}
		return page;
	}


	//商品名と大カテゴリとブランド名で検索
	public Integer countPageNameLargeBrand(String itemName, String categoryNameLarge, String brand) {
		int page = itemRepository.countPageNameLargeBrand(("%"+itemName+"%"), categoryNameLarge, ("%"+brand+"%"));
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	//商品名と中カテゴリとブランド名で検索
	public Integer countPageNameMediumBrandInteger(String itemName, Integer parentId, String brand) {
		int page = itemRepository.countPageNameMediumBrandInteger(("%"+itemName+"%"), parentId, brand);
		if (page == 0) {
			page = 1;
		}
		return page;
	}
	public Integer countPageNameMediumBrand(String itemName, String categoryName, String brand) {
		int page = itemRepository.countPageNameMediumBrand(("%"+itemName+"%"), categoryName, brand);
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	//商品名と小カテゴリとブランド名で検索
	public Integer countPageNameSmallBrand(String itemName, String categoryName, String brand) {
		int page = itemRepository.countPageNameSmallBrand(("%"+itemName+"%"), categoryName, ("%"+brand+"%"));
		if (page == 0) {
			page = 1;
		}
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


	/**
	 * カーソルの移動処理をします.
	 * 
	 * @param arrow カーソル
	 * @return      カーソルの移動
	 */
	private Integer moveArrow(Integer arrow) {
		int move = arrow * 30;
		return arrow = move;
	}

}
