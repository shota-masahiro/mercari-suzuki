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

		//商品名とブランド名で検索
		if (brand != null && itemName != null) {
			return itemRepository.findByNameBrandPage(("%"+itemName+"%"), ("%"+ brand +"%"), arrow);
		}

		//商品検索
		if (itemName != null) {
			return itemRepository.findByNamePage(("%"+itemName+"%"), arrow);
		}

		//

		if (brand != null) {
			System.out.println("koko");
			return itemRepository.findByBrand(arrow, ("%"+brand+"%"));
		} else if (categorys[0] != null) {
			return itemRepository.findByCategoryLarge(categorys[0], arrow);
		} else if (categorys[1] != null) {
			return itemRepository.findByCategoryMedium(categorys[1], arrow);
		} else if (categorys[2] != null) {
			return itemRepository.findByCategorySmall(categorys[2], arrow);
		}
		System.out.println("arrow:" + arrow);
		return itemRepository.findByPage(arrow);
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

	public Integer countPageSmall(String small) {
		int page = itemRepository.countPageSmall(small);
		if (page == 0) {
			page = 1;
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
