package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品詳細情報を操作するサービス.
 * 
 * @author shota.suzuki
 *
 */
@Service
public class ShowItemDetailService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 検索処理をします.
	 * @param <Item>
	 * 
	 * @param id 商品ID
	 * @return   itemオブジェクト
	 */
	public Item findById(String id) {
		return itemRepository.findById(Integer.parseInt(id));
	}
	
}
