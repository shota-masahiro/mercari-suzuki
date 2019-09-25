package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	// 動作確認用メソッド
	public List<Item> preItem() {
		return itemRepository.preItem();
	}
	
}
