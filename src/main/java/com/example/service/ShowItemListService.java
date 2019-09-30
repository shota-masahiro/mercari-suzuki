package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.CategoryName;
import com.example.domain.Item;
import com.example.domain.TestItem;
import com.example.domain.TestNameAll;
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


	public List<TestItem> search(Integer arrow, String itemName, TestNameAll nameAll, String brand, String countPage) {
		arrow = moveArrow(arrow);
		return itemRepository.search(arrow, itemName, nameAll, brand, countPage);
	}

	public Integer searchCount(Integer arrow, String itemName, TestNameAll nameAll, String brand, String countPage) {
		return itemRepository.searchCount(arrow, itemName, nameAll, brand, countPage);
	}

	public TestNameAll searchName(Integer[] categoryIds) {
		return itemRepository.searchName(categoryIds);
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


	private Integer moveArrow(Integer arrow) {// カーソルの移動処理
		int move = arrow * 30;
		return arrow = move;
	}

}