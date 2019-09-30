package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.repository.ItemRepository;

/**
 * ページ数を数えるサービス
 * 
 * @author shota.suzuki
 *
 */
@Service
public class CountPageService {

	@Autowired
	private ItemRepository itemRepository;

	@Async
	public void run(String itemName, Integer[] categorys, String brand, Model model) {

//		if (itemName != null && categorys[2] != null && brand != null) {// 商品名 + 大中小カテゴリ + ブランド名
//			int page = itemRepository.countPageNameSmallBrand(("%"+itemName+"%"), categorys[2], ("%"+brand+"%"));
//			return countPlasPage(page);
//		}
//
//		if (itemName != null && categorys[1] != null && brand != null) {// 商品名 + 大中カテゴリ + ブランド名
//			int page = itemRepository.countPageNameMediumBrand(("%"+itemName+"%"), categorys[1], ("%"+brand+"%"));
//			return countPlasPage(page);
//		}
//
//		if (itemName != null && categorys[0] != null && brand != null) {// 商品名 + 大カテゴリ + ブランド名
//			int page = itemRepository.countPageNameLargeBrand(("%"+itemName+"%"), categorys[0], ("%"+brand+"%"));
//			return countPlasPage(page);
//		}
//
//		if (itemName != null && categorys[2] != null) {// 商品名 + 大中小カテゴリ
//			int page = itemRepository.countPageNameSmall(("%"+itemName+"%"), categorys[2]);
//			return countPlasPage(page);
//		}
//
//		if (itemName != null && categorys[1] != null) {// 商品名 + 大中カテゴリ
//			int page = itemRepository.countPageNameMedium(("%"+itemName+"%"), categorys[1]);
//			return countPlasPage(page);
//		}
//
//		if (itemName != null && categorys[0] != null) {// 商品名 + 大カテゴリ
//			int page = itemRepository.countPageNameLarge(("%"+itemName+"%"), categorys[0]);
//			return countPlasPage(page);
//		}
//
//		if (categorys[2] != null && brand != null) {// 大中小カテゴリ + ブランド名
//			int page = itemRepository.countPageSmallBrand(categorys[2], ("%"+brand+"%"));
//			return countPlasPage(page);
//		}
//
//		if (categorys[1] != null && brand != null) {// 大中カテゴリ + ブランド名
//			int page = itemRepository.countPageMediumBrand(categorys[1], ("%"+brand+"%"));
//			return countPlasPage(page);
//		}
//
//		if (categorys[0] != null && brand != null) {// 大カテゴリ + ブランド名
//			int page = itemRepository.countPageLargeBrand(categorys[0], ("%"+brand+"%"));
//			return countPlasPage(page);
//		}
//
//		if (itemName != null && brand != null) {// 商品名 + ブランド名 
//			int page = itemRepository.countPageNameBrand(("%"+itemName+"%"), ("%"+ brand +"%"));
//			return countPlasPage(page);
//		}
//
//		if (categorys[2] != null) {// 大中小カテゴリのみ 
//			int page = itemRepository.countPageSmall(categorys[2]);
//			return countPlasPage(page);
//		}
//
//		if (categorys[1] != null) {// 大中カテゴリのみ
//			int page = itemRepository.countPageMedium(categorys[1]);
//			return countPlasPage(page);
//		}
//
//		if (categorys[0] != null) {// 大カテゴリのみ
//			int page = itemRepository.countPageLarge(categorys[0]);
//			return countPlasPage(page);
//		}
//
//		if (brand != null) {// ブランド名のみ
//			int page = itemRepository.countPageBrand(("%"+brand+"%"));
//			return countPlasPage(page);
//		}
//
//		if (itemName != null) {// 商品名のみ
//			int page = itemRepository.countPageName(("%"+itemName+"%"));
//			return countPlasPage(page);
//		}

		Integer totalPages = itemRepository.countPage();// 全件検索のページ数
		model.addAttribute("totalPages", totalPages);
	}

	private int countPlasPage(int page) {//1ページ＋するメソッド
		if (page == 1) {
			page++;
		}
		return page;
	}

}
