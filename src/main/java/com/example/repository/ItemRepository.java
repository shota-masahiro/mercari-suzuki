package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.CategoryName;
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


	private static final RowMapper<Item> ITEM_CATEGORY_ROW_MAPPER2 = (rs, i) -> {
		Item item = new Item();
		item.setMediumCategory(rs.getString("mediumCategory"));
		return item;
	};


	private static final RowMapper<CategoryName> CATEGORY_NAME_ROW_MAPPER = (rs, i) -> {
		CategoryName categoryName = new CategoryName();
		categoryName.setCategoryLargeName(rs.getString("largeText"));
		return categoryName;
	};

	private static final RowMapper<CategoryName> CATEGORY_NAME_ROW_MAPPER2 = (rs, i) -> {
		CategoryName categoryName = new CategoryName();
		categoryName.setCategoryLargeName(rs.getString("largeText"));
		categoryName.setCategoryMediumName(rs.getString("mediumText"));
		categoryName.setParentId(rs.getInt("parent_id"));
		return categoryName;
	};

	private static final RowMapper<CategoryName> CATEGORY_NAME_ROW_MAPPER3 = (rs, i) -> {
		CategoryName categoryName = new CategoryName();
		categoryName.setCategoryLargeName(rs.getString("largeText"));
		categoryName.setCategoryMediumName(rs.getString("mediumText"));
		categoryName.setCategorySmallName(rs.getString("smallText"));
		categoryName.setParentId(rs.getInt("parent_id"));
		return categoryName;
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
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
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
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	/**
	 * 検索処理をします.
	 * 
	 * @param brand ブランド
	 * @return      商品情報一覧
	 */
	public List<Item> findByBrand(Integer arrow, String brand) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE i.brand like :brand ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("brand", brand).addValue("arrow", arrow);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	/**
	 * 検索処理をします.
	 * 
	 * @param name  商品名
	 * @param arrow カーソル
	 * @return      商品情報一覧
	 */
	public List<Item> findByNamePage(String name, Integer arrow) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE i.name like :name ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("name", name);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	/**
	 * 検索処理をします.
	 * 
	 * @param name  商品名
	 * @param arrow カーソル
	 * @return      商品情報一覧
	 */
	public List<Item> findByNameBrandPage(String name, String brand, Integer arrow) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :name AND i.brand LIKE :brand ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("name", name).addValue("brand", brand);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	//商品名とカテゴリー大で検索します.
	public List<Item> findByNameCategoryLarge(Integer arrow, String itemName, String categoryName) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND split_part(c.name_all, '/', 1) = :categoryName ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("categoryName", categoryName);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}

	//商品名と中カテゴリーで検索します.
	public List<Item> findByNameCategoryMediumInteger(Integer arrow, Integer parentId, String itemName) {
		StringBuilder sql = new StringBuilder();
		//i.conditionをi.condition_idに変更した！けど自宅PC用だから意味ないよ！
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND c.parent_id = :parentId ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentId", parentId).addValue("arrow", arrow).addValue("itemName", itemName);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}

	//商品名とカテゴリー小で検索します.
	public List<Item> findByNameCategorySmall(Integer arrow, String itemName, String categoryName) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2) || '/' || split_part(c.name_all, '/', 3)) = :categoryName ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("categoryName", categoryName);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}

	//商品名と中カテゴリーで検索します.
	public List<Item> findByNameCategoryMedium(Integer arrow, String categoryName, String itemName) {
		StringBuilder sql = new StringBuilder();
		//i.conditionをi.condition_idに変更した！けど自宅PC用だから意味ないよ！
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2)) = :categoryName ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryName", categoryName).addValue("arrow", arrow).addValue("itemName", itemName);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	//商品名とカテゴリー大とブランド名で検索します.
	public List<Item> findByNameCategoryLargeBrand(Integer arrow, String itemName, String categoryName, String brand) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND split_part(c.name_all, '/', 1) = :categoryName AND i.brand LIKE :brand ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("categoryName", categoryName).addValue("brand", brand);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}

	//商品名と中カテゴリーとブランド名で検索します.
	public List<Item> findByNameCategoryMediumBrandInteger(Integer arrow, String itemName, Integer parentId, String brand) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND c.parent_id = :parentId AND i.brand LIKE :brand ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("parentId", parentId).addValue("brand", brand);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	//商品名と中カテゴリーとブランド名で検索します.
	public List<Item> findByNameCategoryMediumBrand(Integer arrow, String itemName, String categoryName, String brand) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2)) = :categoryName AND i.brand LIKE :brand ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("categoryName", categoryName).addValue("brand", brand);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}

	//商品名と小カテゴリーとブランド名で検索します.
	public List<Item> findByNameCategorySmallBrand(Integer arrow, String itemName, String categoryName, String brand) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2) || '/' || split_part(c.name_all, '/', 3)) = :categoryName AND i.brand LIKE :brand ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("categoryName", categoryName).addValue("brand", brand);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	// 大カテゴリ用の検索メソッド
	public List<Item> findByCategoryLarge(String largeCategory, Integer arrow) {
		StringBuilder sql = new StringBuilder();
		//i.conditionをi.condition_idに変更した！けど自宅PC用だから意味ないよ！
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE split_part(c.name_all, '/', 1) = :largeCategory ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("largeCategory", largeCategory).addValue("arrow", arrow);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	// 中カテゴリ用の検索メソッド
	public List<Item> findByCategoryMedium(String mediumCategory, Integer arrow) {
		StringBuilder sql = new StringBuilder();
		//i.conditionをi.condition_idに変更した！けど自宅PC用だから意味ないよ！
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2)) = :mediumCategory ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("mediumCategory", mediumCategory).addValue("arrow", arrow);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}

	// 中カテゴリ用の検索メソッド
	public List<Item> findByCategoryMediumInteger(Integer parentId, Integer arrow) {
		StringBuilder sql = new StringBuilder();
		//i.conditionをi.condition_idに変更した！けど自宅PC用だから意味ないよ！
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE c.parent_id = :parentId ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentId", parentId).addValue("arrow", arrow);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	// 中カテゴリ用の検索メソッド!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Item findByCategoryMediumIntegerSecond(Integer parentId) {
		StringBuilder sql = new StringBuilder();
		//i.conditionをi.condition_idに変更した！けど自宅PC用だから意味ないよ！
		sql.append("SELECT split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2) mediumCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE c.parent_id = :parentId ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 1 OFFSET 0;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentId", parentId);
		Item item = template.queryForObject(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER2);
		return item;
	}


	// 小カテゴリ用の検索メソッド
	public List<Item> findByCategorySmall(String smallCategory, Integer arrow) {
		StringBuilder sql = new StringBuilder();
		//i.conditionをi.condition_idに変更した！けど自宅PC用だから意味ないよ！
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c.id c_id, c.parent_id c_parent_id, c.category_name c_category_name, c.name_all c_name_all, split_part(c.name_all, '/', 1) largeCategory, split_part(c.name_all, '/', 2) mediumCategory, split_part(c.name_all, '/', 3) smallCategory ");
		sql.append("FROM items i left outer join category c on i.category_id = c.id ");
		sql.append("WHERE (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2) || '/' || split_part(c.name_all, '/', 3)) = :smallCategory ");
		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("smallCategory", smallCategory).addValue("arrow", arrow);
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
		sql.append("SELECT (count(id) / 30) ");
		sql.append("FROM items;");
		SqlParameterSource param = new MapSqlParameterSource();
		return template.queryForObject(sql.toString(), param, Integer.class);
	}


	/**
	 * 総ページ数を取得します.
	 * 
	 * @param brand ブランド
	 * @return 総ページ数
	 */
	public Integer countPageBrand(String brand) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(id) / 30 ");
		sql.append("FROM items ");
		sql.append("WHERE brand like :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}

	// 大カテゴリの総ページ数を取得
	public Integer countPageLarge(String large) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE split_part(c.name_all, '/', 1) = :large;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("large", large);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}

	// 中カテゴリの総ページ数を取得
	public Integer countPageMedium(String medium) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2)) = :medium;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("medium", medium);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}

	// 中カテゴリの総ページ数を取得
	public Integer countPageMediumInteger(Integer parentId) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE c.parent_id = :parentId;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("parentId", parentId);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}

	// 小カテゴリ
	public Integer countPageSmall(String small) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2) || '/' || split_part(c.name_all, '/', 3)) = :small;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("small", small);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}

	// 商品名検索
	public Integer countPageName(String name) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE i.name like :name;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}

	// 商品名とブランド名検索
	public Integer countPageNameBrand(String name, String brand) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i INNER JOIN category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :name AND i.brand LIKE :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name).addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}


	// 商品名と大カテゴリの総ページ数を取得
	public Integer countPageNameLarge(String itemName, String categoryNameLarge) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND split_part(c.name_all, '/', 1) = :categoryNameLarge;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("categoryNameLarge", categoryNameLarge);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}

	// 商品名と中カテゴリの総ページ数を取得
	public Integer countPageNameMediumInteger(String itemName, Integer parentId) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND c.parent_id = :parentId;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("parentId", parentId);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	// 商品名と中カテゴリの総ページ数を取得
	public Integer countPageNameMedium(String itemName, String categoryName) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2)) = :categoryName;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("categoryName", categoryName);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}


	// 商品名と小カテゴリの総ページ数を取得
	public Integer countPageNameSmall(String itemName, String categoryName) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2) || '/' || split_part(c.name_all, '/', 3)) = :categoryName;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("categoryName", categoryName);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}


	// 商品名と大カテゴリとブランド名の総ページ数を取得
	public Integer countPageNameLargeBrand(String itemName, String categoryNameLarge, String brand) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND split_part(c.name_all, '/', 1) = :categoryNameLarge AND i.brand LIKE :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("categoryNameLarge", categoryNameLarge).addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}

	// 商品名と中カテゴリの総ページ数を取得
	public Integer countPageNameMediumBrandInteger(String itemName, Integer parentId, String brand) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND c.parent_id = :parentId AND i.brand LIKE :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("parentId", parentId).addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	// 商品名と中カテゴリの総ページ数を取得
	public Integer countPageNameMediumBrand(String itemName, String categoryName, String brand) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2)) = :categoryName AND i.brand LIKE :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("categoryName", categoryName).addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}

	// 商品名と小カテゴリとブランド名の総ページ数を取得
	public Integer countPageNameSmallBrand(String itemName, String categoryName, String brand) {
		StringBuilder sql = new StringBuilder();
		//+1をしてないと割り切れなかった分がうまく表示されない
		sql.append("SELECT count(i.id) / 30 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2) || '/' || split_part(c.name_all, '/', 3)) = :categoryName AND i.brand LIKE :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("categoryName", categoryName).addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}


	//大カテゴリネーム取得
	public List<CategoryName> categoryLargeText() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct split_part(name_all, '/', 1) largeText ");
		sql.append("FROM category ");
		sql.append("WHERE split_part(name_all, '/', 1) IS NOT NULL;");
		List<CategoryName> categoryLargeList = template.query(sql.toString(), CATEGORY_NAME_ROW_MAPPER);
		return categoryLargeList;
	}

	//中カテゴリネーム取得
	public List<CategoryName> categoryMediumText() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct split_part(name_all, '/', 1) largeText, split_part(name_all, '/', 2) mediumText, parent_id ");
		sql.append("FROM category ");
		sql.append("WHERE split_part(name_all, '/', 2) IS NOT NULL;");
		List<CategoryName> categoryLargeList = template.query(sql.toString(), CATEGORY_NAME_ROW_MAPPER2);
		return categoryLargeList;
	}

	//小カテゴリネーム取得
	public List<CategoryName> categorySmallText() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct split_part(name_all, '/', 1) largeText, split_part(name_all, '/', 2) mediumText, split_part(name_all, '/', 3) smallText, parent_id ");
		sql.append("FROM category ");
		sql.append("WHERE split_part(name_all, '/', 3) IS NOT NULL;");
		List<CategoryName> categoryLargeList = template.query(sql.toString(), CATEGORY_NAME_ROW_MAPPER3);
		return categoryLargeList;
	}

	/**
	 * 更新処理をします.
	 * 
	 * @param item itemオブジェクト
	 */
	public void update(Item item) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE items ");
		sql.append("SET name=:name, condition=:condition, category_id=:categoryId, brand=:brand, price=:price, shipping=:shipping, description=:description ");
		sql.append("WHERE id=:id;");
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql.toString(), param);
	}


	/**
	 * カテゴリIDを取得します.
	 * 
	 * @param categoryName カテゴリ名(大/中/小)
	 * @return             カテゴリID
	 */
	public Integer findByCategoryAllName(String categoryName) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id ");
		sql.append("FROM category ");
		sql.append("WHERE name_all = :categoryName;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryName", categoryName);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}


	/**
	 * 挿入処理をします.
	 * 
	 * @param item itemオブジェクト
	 */
	public void insert(Item item) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO items(name, condition, category_id, brand, price, shipping, description) ");
		sql.append("VALUES(:name, :condition, :categoryId, :brand, :price, :shipping, :description);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql.toString(), param);
	}

}
