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
import com.example.domain.Item;
import com.example.domain.CategoryNameAll;

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
	private static final RowMapper<Item> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(Item.class);

	/** NameAllオブジェクトを生成するローマッパー */
	private static final RowMapper<CategoryNameAll> NAME_ALL_ROW_MAPPER = new BeanPropertyRowMapper<>(CategoryNameAll.class);

	/** categoryNameオブジェクトを生成するローマッパー */
	private static final RowMapper<CategoryName> CATEGORY_NAME_ROW_MAPPER = new BeanPropertyRowMapper<>(CategoryName.class);


	//検索実行用のメソッド カテゴリー大中小の値を取得
	public CategoryNameAll searchName(Integer[] categoryIds) {
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

	/** 商品検索とページ数の値を取得します. */
	public List<Item> search(Integer arrow, String itemName, CategoryNameAll nameAll, String brand, String countPage) {// 検索の実行メソッド 商品一覧を取得する
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = createSQL(arrow, itemName, nameAll, brand, countPage, params);
		return template.query(sql, params, ITEM_ROW_MAPPER);
	}
	public Integer searchCount(Integer arrow, String itemName, CategoryNameAll nameAll, String brand, String countPage) {// 検索の実行メソッド ページ数を取得する
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = createSQL(arrow, itemName, nameAll, brand, countPage, params);
		return template.queryForObject(sql, params, Integer.class);
	}
	private String createSQL(Integer arrow, String itemName, CategoryNameAll nameAll, String brand, String countPage, MapSqlParameterSource params) {// SQLを整形する
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

	/**
	 * 検索処理をします.
	 * 
	 * @param id itemID
	 * @return   itemオブジェクト
	 */
	public Item findById(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id itemId, i.name itemName, i.condition condition, i.category_id categoryId, i.brand brand, i.price price, i.shipping shipping, i.description description, ");
		sql.append("c2.parent_id largeCategoryId, c.parent_id mediumCategoryId, c.id smallCategoryId, c.name_all nameAll ");
		sql.append("FROM items i LEFT OUTER JOIN category c ON i.category_id = c.id ");
		sql.append("LEFT OUTER JOIN category c2 ON c.parent_id = c2.id ");
		sql.append("WHERE i.id = :id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_ROW_MAPPER);
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
		sql.append("SET name=:itemName, condition=:condition, category_id=:categoryId, brand=:brand, price=:price, shipping=:shipping, description=:description ");
		sql.append("WHERE id=:itemId;");
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
		sql.append("SELECT id ");
		sql.append("FROM category ");
		sql.append("WHERE id = :categoryId;");
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
		sql.append("VALUES(:itemName, :condition, :categoryId, :brand, :price, :shipping, :description);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql.toString(), param);
	}

}