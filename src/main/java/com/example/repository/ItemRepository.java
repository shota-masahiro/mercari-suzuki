package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.CategoryName;
import com.example.domain.Item;
import com.example.domain.TestItem;
import com.example.domain.TestNameAll;

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

	/** itemオブジェクトを生成するローマッパー */
	private static final RowMapper<TestItem> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(TestItem.class);

	/** NameAllオブジェクトを生成するローマッパー */
	private static final RowMapper<TestNameAll> NAME_ALL_ROW_MAPPER = new BeanPropertyRowMapper<>(TestNameAll.class);

	/** categoryNameオブジェクトを生成するローマッパー */
	private static final RowMapper<CategoryName> CATEGORY_NAME_ROW_MAPPER = new BeanPropertyRowMapper<>(CategoryName.class);


	//検索実行用のメソッド カテゴリー大中小の値を取得
	public TestNameAll searchName(Integer[] categoryIds) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		if (categoryIds[0] == null) {
			return null;
		}
		String sql = createNameAllSQL(categoryIds, params);
		return template.queryForObject(sql, params, NAME_ALL_ROW_MAPPER);
	}
	private String createNameAllSQL(Integer[] categoryIds, MapSqlParameterSource params) {// SQLを整形する
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c1.category_name largeName, c2.category_name midiumName, c2.name_all smallName ");
		sql.append("FROM category c1 LEFT OUTER JOIN category c2 ON c1.id = c2.parent_id ");

		if (categoryIds[2] != null) {
			sql.append("WHERE c2.id=:smallId;");
			params.addValue("smallId", categoryIds[2]);
		} else if (categoryIds[1] != null) {
			sql.append("WHERE c1.id=:largeId AND c2.id=:midiumId;");
			params.addValue("largeId", categoryIds[0]).addValue("midiumId", categoryIds[1]);
		} else if (categoryIds[0] != null) {
			StringBuilder sql2 = new StringBuilder("SELECT category_name largeName FROM category WHERE id=:largeId;");
			params.addValue("largeId", categoryIds[0]);
			return sql2.toString();
		}
		return sql.toString();
	}


	public List<TestItem> search(Integer arrow, String itemName, TestNameAll nameAll, String brand, String countPage) {// 検索の実行メソッド 商品一覧を取得する
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = createSQL(arrow, itemName, nameAll, brand, countPage, params);
		return template.query(sql, params, ITEM_ROW_MAPPER);
	}
	public Integer searchCount(Integer arrow, String itemName, TestNameAll nameAll, String brand, String countPage) {// 検索の実行メソッド ページ数を取得する
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = createSQL(arrow, itemName, nameAll, brand, countPage, params);
		return template.queryForObject(sql, params, Integer.class);
	}
	private String createSQL(Integer arrow, String itemName, TestNameAll nameAll, String brand, String countPage, MapSqlParameterSource params) {// SQLを整形する
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT i.id itemId, i.name itemName, i.condition condition, i.category_id categoryId, i.brand brand, i.price price, i.shipping shipping, i.description description, ");
		sql.append("c2.parent_id largeCategoryId, c.parent_id mediumCategoryId, c.id smallCategoryId, c.name_all nameAll ");
		sql.append("FROM items i LEFT OUTER JOIN category c ON i.category_id = c.id ");
		sql.append("LEFT OUTER JOIN category c2 ON c.parent_id = c2.id ");
		sql.append("WHERE 1=1 ");

		// 商品名(あいまい検索)
		if (itemName != null) {
			sql.append("AND i.name LIKE :itemName ");
			params.addValue("itemName", "%"+itemName+"%");
		}

		//カテゴリー名
		if (nameAll != null) {
			if (nameAll.getSmallName() != null) {
				sql.append("AND c.name_all LIKE :nameAll ");
				params.addValue("nameAll", nameAll.getSmallName());
			} else if (nameAll.getMidiumName() != null) {
				sql.append("AND c.name_all LIKE :nameAll ");
				params.addValue("nameAll", nameAll.getLargeMediumName()+"%");
			} else {
				sql.append("AND c.name_all LIKE :nameAll ");
				params.addValue("nameAll", nameAll.getLargeName()+"%");
			}
		}

		//ブランド名
		if (brand != null) {
			sql.append("AND i.brand LIKE :brand ");
			params.addValue("brand", brand);
		}

		//ページ数を取得
		if ("count".equals(countPage)) {
			String countSQL = sql.toString();
			countSQL = countSQL.replaceFirst("SELECT.+FROM", "SELECT count(*) FROM");
			return countSQL;
		} else {
			sql.append("ORDER BY i.id ");
			sql.append("LIMIT 30 OFFSET :arrow;");
			params.addValue("arrow", arrow);
		}

		return sql.toString();
	}


	/** Itemオブジェクトを生成するローマッパー. */
	private static final RowMapper<Item> ITEM_CATEGORY_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("i_id"));
		item.setName(rs.getString("i_name"));
		item.setCondition(rs.getInt("i_condition"));
		item.setCategoryId(rs.getInt("categoryId"));
		item.setCategory(rs.getString("c_name_all"));
		item.setBrand(rs.getString("i_brand"));
		item.setPrice(rs.getInt("i_price"));
		item.setShipping(rs.getInt("i_shipping"));
		item.setDescription(rs.getString("i_description"));
		item.setLargeCategory(rs.getString("largeCategory"));
		item.setMediumCategory(rs.getString("mediumCategory"));
		item.setSmallCategory(rs.getString("smallCategory"));
		item.setParentId(rs.getInt("c_parent_id"));
		item.setSmallCategoryId(rs.getInt("c_id"));
		return item;
	};

	public StringBuilder getSQL() {// 共通用のSQLを取得
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.condition i_condition, i.category_id i_category_id, i.brand i_brand, i.price i_price, i.shipping i_shipping, i.description i_description,");
		sql.append("c1.id c_id, c1.parent_id c_parent_id, c1.category_name c_category_name, c1.name_all c_name_all, split_part(c1.name_all, '/', 1) largeCategory, split_part(c1.name_all, '/', 2) mediumCategory, split_part(c1.name_all, '/', 3) smallCategory, c2.parent_id categoryId ");
		sql.append("FROM category c1 left OUTER join category c2 on c1.parent_id = c2.id LEFT OUTER JOIN items i on c1.id = i.category_id ");
		return sql;
	}

	/**
	 * 検索処理をします.
	 * 
	 * @param id itemID
	 * @return   itemオブジェクト
	 */
	public Item findById(Integer id) {
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE i.id = :id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		if (itemList.size() != 0) {
			return itemList.get(0);
		} else {
			return null;
		}
	}



	/** カテゴリーネームを取得するメソッド */
	public List<CategoryName> getCategoryName(String checkKey) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String sql = createCategoryNameSQL(checkKey);
		return template.query(sql, param, CATEGORY_NAME_ROW_MAPPER);
	}
	public String createCategoryNameSQL(String checkKey) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, parent_id parentId, category_name categoryName, name_all nameAll FROM category ");
		if ("large".equals(checkKey)) {
			sql.append("WHERE parent_id IS NULL AND name_all IS NULL;");
		} else if("medium".equals(checkKey)) {
			sql.append("WHERE name_all IS NULL AND parent_id IS NOT NULL;");
		} else if("small".equals(checkKey)) {
			sql.append("WHERE parent_id IS NOT NULL AND name_all IS NOT NULL;");
		}
		return sql.toString();
	}


	/**
	 * 更新処理をします.
	 * 
	 * @param item itemオブジェクト
	 */
	public void update(Item item) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE items ");
		sql.append("SET name=:name, condition=:condition, category_id=:categoryId, brand=:brand, price=:price, shipping=:shipping, description=:description WHERE id=:id;");
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql.toString(), param);
	}


	/**
	 * カテゴリIDを取得します.
	 * 
	 * @param categoryId カテゴリ名(大/中/小)
	 * @return             カテゴリID
	 */
	public Integer findByCategoryAllName(Integer categoryId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id FROM category WHERE id = :categoryId;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId);
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