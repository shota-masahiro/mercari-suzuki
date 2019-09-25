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


	/**
	 * 検索処理をします.
	 * 
	 * @param arrow カーソル
	 * @return      商品情報一覧
	 */
	public List<Item> findByPage(Integer arrow) {

		//カーソル移動処理
		if (arrow != 0) {
			arrow = moveArrow(arrow);
		}

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
	 * カーソルの移動処理をします.
	 * 
	 * @param arrow カーソル
	 * @return      カーソルの移動
	 */
	private Integer moveArrow(Integer arrow) {
		int move = arrow * 30;
		return arrow += move;

//		int i = 1;
//		int move = arrow;
//		while (true) {
//
//			if (arrow == i) {
//				move =+ i*30;
//				break;
//			}
//			i++;
//		}
//		return move;
	}

}
