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

	/** 商品検索をします. */
	public List<TestItem> search(Integer arrow, String itemName, TestNameAll nameAll, String brand, String countPage) {
		arrow = moveArrow(arrow);
		return itemRepository.search(arrow, itemName, nameAll, brand, countPage);
	}
	
	/** ページ数を取得します. */
	public Integer searchCount(Integer arrow, String itemName, TestNameAll nameAll, String brand, String countPage) {
		return itemRepository.searchCount(arrow, itemName, nameAll, brand, countPage);
	}
	
	/** カテゴリー名を取得します. */
	public TestNameAll searchName(Integer[] categoryIds) {
		return itemRepository.searchName(categoryIds);
	}
	
	/** カテゴリー情報を取得します. */
	public List<CategoryName> getCategoryName(String checkKey) {
		return itemRepository.getCategoryName(checkKey);
	}


	private Integer moveArrow(Integer arrow) {// カーソルの移動処理
		int move = arrow * 30;
		return arrow = move;
	}

}