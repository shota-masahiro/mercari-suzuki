package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

/**
 * itemsテーブルを操作するリポジトリ.
 * 
 * @author shota.suzuki
 *
 */
@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * Itemオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Item> ITEM_CATEGORY_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("i_id"));
		item.setName(rs.getString("i_name"));
		item.setCondition(rs.getInt("i_condition"));
		item.setCategoryId(rs.getInt("i_category_id"));
		item.setCategory(rs.getString("c_name_all"));
		item.setBrand(rs.getString("i_brand"));
		item.setPrice(rs.getInt("i_price"));
		item.setShipping(rs.getInt("i_shipping"));
		item.setDescription(rs.getString("i_description"));
		item.setLargeCategory(rs.getString("largeCategory"));
		item.setMediumCategory(rs.getString("mediumCategory"));
		item.setSmallCategory(rs.getString("smallCategory"));
		return item;
	};


	/**
	 * 検索処理をします.
	 * 
	 * @param id itemID
	 * @return   itemオブジェクト
	 */
	public Item findById(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE i.id=:id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		if (itemList.size() != 0) {
			return itemList.get(0);
		} else {
			return null;
		}
	}


	/**
	 * 検索処理をします.
	 * 
	 * @param arrow カーソル
	 * @return      itemオブジェクト
	 */
	public List<Item> findByPage(Integer arrow) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	
	
	/**
	 * 総ページ数を取得します.
	 * 
	 * @return 総ページ数
	 */
	public Integer countPage() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(id) / 30");
		sql.append("FROM items;");
		SqlParameterSource param = new MapSqlParameterSource();
		return template.queryForObject(sql.toString(), param, Integer.class);
	}


	/**
	 * 更新処理をします.
	 * 
	 * @param item itemオブジェクト
	 */
	public void update(Item item) {
		StringBuilder sql = new StringBuilder();
		sql.append("");
		sql.append("");
		sql.append("");
	}

}
