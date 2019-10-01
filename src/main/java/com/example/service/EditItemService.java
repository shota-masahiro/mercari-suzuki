package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.form.AddEditItemForm;
import com.example.repository.ItemRepository;

/**
 * 商品更新情報を操作するサービス.
 * 
 * @author shota.suzuki
 *
 */
@Service
public class EditItemService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 更新処理をします.
	 * 
	 * @param form リクエストパラメータ
	 */
	public void update(AddEditItemForm form) {
		Item item = new Item();
		form.setJoinCategory();
		BeanUtils.copyProperties(form, item);

		System.out.println("form:"+form);

		int categoryId = itemRepository.findByCategoryAllName(form.getIntegerSmallCategory()); // kokoでエラーが起きている
		item.setCategoryId(categoryId);

		item.setId(form.getIntegerId());
		item.setCondition(form.getIntegerCondition());
		item.setPrice(form.getIntegerPrice());
		item.setShipping(form.getIntegerShipping());

		itemRepository.update(item);
	}

}