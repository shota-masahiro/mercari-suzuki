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
	public List<Item> findByPage(Integer arrow, String brand, String[] categorys, String itemName) {

		//カーソル移動処理
		if (arrow != 0) {
			arrow = moveArrow(arrow);
		}

		//商品名と大中小カテゴリとブランド名で検索
		if (itemName != null && categorys[2] != null && brand != null) {
			System.out.println("ここが実行される");
			return itemRepository.findByNameCategorySmallBrand(arrow, ("%"+itemName+"%"), categorys[2], ("%"+brand+"%"));
		}

		//商品名と大中カテゴリとブランド名で検索
		if (itemName != null && categorys[1] != null && brand != null) {
			try {
				return itemRepository.findByNameCategoryMediumBrandInteger(arrow, ("%"+itemName+"%"), Integer.parseInt(categorys[1]), ("%"+brand+"%"));
			} catch (Exception e) {
				return itemRepository.findByNameCategoryMediumBrand(arrow, ("%"+itemName+"%"), categorys[1], ("%"+brand+"%"));
			}
		}

		//商品名と大カテゴリとブランド名で検索
		if (itemName != null && categorys[0] != null && brand != null) {
			return itemRepository.findByNameCategoryLargeBrand(arrow, ("%"+itemName+"%"), categorys[0], ("%"+brand+"%"));
		}

		//商品名と大中小カテゴリで検索
		if (itemName != null && categorys[2] != null) {
			return itemRepository.findByNameCategorySmall(arrow, ("%"+itemName+"%"), categorys[2]);
		}

		//商品名と大中カテゴリで検索
		if (itemName != null && categorys[1] != null) {
			try {
				return itemRepository.findByNameCategoryMediumInteger(arrow, Integer.parseInt(categorys[1]), ("%"+itemName+"%"));
			} catch (Exception e) {
				return itemRepository.findByNameCategoryMedium(arrow, categorys[1], ("%"+itemName+"%"));
			}
		}

		//商品名と大カテゴリで検索
		if (itemName != null && categorys[0] != null) {
			return itemRepository.findByNameCategoryLarge(arrow, ("%"+itemName+"%"), categorys[0]);
		}

		//商品名とブランド名で検索
		if (brand != null && itemName != null) {
			return itemRepository.findByNameBrandPage(("%"+itemName+"%"), ("%"+ brand +"%"), arrow);
		}

		//商品検索
		if (itemName != null) {
			return itemRepository.findByNamePage(("%"+itemName+"%"), arrow);
		}

		if (brand != null) {
			return itemRepository.findByBrand(arrow, ("%"+brand+"%"));
		} else if (categorys[0] != null) {
			if (categorys[2] != null && !"---".equals(categorys[2])) {
				return itemRepository.findByCategorySmall(categorys[2], arrow);
			}
			try {
				if (categorys[1] != null) {
					int parentId = Integer.parseInt(categorys[1]);
					return itemRepository.findByCategoryMediumInteger(parentId, arrow);
				}

			} catch (Exception e) {
				return itemRepository.findByCategoryMedium(categorys[1], arrow);
			}

			return itemRepository.findByCategoryLarge(categorys[0], arrow);
		} else if (categorys[1] != null) {
			return itemRepository.findByCategoryMedium(categorys[1], arrow);
		} else if (categorys[2] != null) {
			return itemRepository.findByCategorySmall(categorys[2], arrow);
		}
		System.out.println("arrow:" + arrow);
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


	public Integer countPageLarge(String large) {
		int page = itemRepository.countPageLarge(large);
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	public Integer countPageMedium(String medium) {
		int page = itemRepository.countPageMedium(medium);
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	public Integer countPageMediumInteger(Integer parentId) {
		int page = itemRepository.countPageMediumInteger(parentId);
		if (page == 0) {
			page = 1;
		}
		return page;
	}

	public Integer countPageSmall(String small) {
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

	//商品名と大カテゴリで検索
	public Integer countPageNameSmall(String itemName, String categoryNameLarge) {
		int page = itemRepository.countPageNameSmall(("%"+itemName+"%"), categoryNameLarge);
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
