package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.form.AddEditItemForm;
import com.example.repository.ItemRepository;

/**
 * 商品追加情報を操作するサービス.
 * 
 * @author shota.suzuki
 *
 */
@Service
public class AddItemService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 挿入処理をします.
	 * 
	 * @param form リクエストパラメータ
	 */
	public void insert(AddEditItemForm form) {
		Item item = new Item();
		BeanUtils.copyProperties(form, item);

		item.setCategoryId(form.getIntegerSmallCategory());
		item.setShipping(0);
		item.setCondition(form.getIntegerCondition());
		item.setPrice(form.getIntegerPrice());

		itemRepository.insert(item);
	}

}