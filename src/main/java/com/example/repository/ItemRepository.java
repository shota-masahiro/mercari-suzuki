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

	private static final RowMapper<TestItem> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(TestItem.class);

	private static final RowMapper<TestNameAll> NAME_ALL_ROW_MAPPER = new BeanPropertyRowMapper<>(TestNameAll.class);


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
		sql.append("c.id largeCategoryId, c.parent_id mediumCategoryId, c2.parent_id smallCategoryId, c.name_all nameAll ");
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
				params.addValue("nameAll", nameAll.getSmallName()+"%");
			} else if (nameAll.getMidiumName() != null) {
				sql.append("AND c.name_all LIKE :nameAll ");
				params.addValue("nameAll", nameAll.getMidiumName()+"%");
			}
			sql.append("AND c.name_all LIKE :nameAll ");
			params.addValue("nameAll", nameAll.getLargeName()+"%");

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
	 * Itemオブジェクトを生成するローマッパー.
	 */
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

	private static final RowMapper<CategoryName> CATEGORY_NAME_ROW_MAPPER = (rs, i) -> {
		CategoryName categoryName = new CategoryName();
		categoryName.setId(rs.getInt("id"));
		categoryName.setCategoryName(rs.getString("category_name"));
		categoryName.setParentId(rs.getInt("parent_id"));
		return categoryName;
	};

	public StringBuilder getSQL() {//共通用のSQLを取得
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


	/**
	 * 検索処理をします.
	 * 
	 * @param arrow カーソル
	 * @return      itemオブジェクト
	 */
	public List<Item> findByPage(Integer arrow) {// 全件検索用のメソッド
		StringBuilder sql = new StringBuilder(getSQL());
		//		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		sql.append("LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	public List<Item> findByNamePage(String name, Integer arrow) {// 商品名用の検索メソッド
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE i.name LIKE :name ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("name", name);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	public List<Item> findByBrand(Integer arrow, String brand) {// ブランド名用の検索メソッド
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE i.brand LIKE :brand ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("brand", brand).addValue("arrow", arrow);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	public List<Item> findByNameBrandPage(Integer arrow, String itemName, String brand) {// 商品名＋ブランド名用の検索メソッド
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE i.name LIKE :itemName AND i.brand LIKE :brand ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("brand", brand);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	public List<Item> findByCategoryLarge(Integer categoryId, Integer arrow) {// 大カテゴリ用の検索メソッド
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE split_part(c1.name_all, '/', 1) = (SELECT category_name FROM category WHERE id=:categoryId) ");
		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId).addValue("arrow", arrow);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	public List<Item> findByCategoryMedium(Integer categoryId, Integer arrow) {// 中カテゴリ用の検索メソッド
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE (split_part(c1.name_all, '/', 1) || '/' || split_part(c1.name_all, '/', 2)) = (SELECT (c2.category_name || '/' || c1.category_name) FROM category c1 LEFT OUTER JOIN category c2 ON c1.parent_id = c2.id WHERE c1.id = :categoryId) ");
		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId).addValue("arrow", arrow);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	public List<Item> findByCategorySmall(Integer categoryId, Integer arrow) {// 小カテゴリ用の検索メソッド
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE (split_part(c1.name_all, '/', 1) || '/' || split_part(c1.name_all, '/', 2) || '/' || split_part(c1.name_all, '/', 3)) = (SELECT name_all FROM category WHERE id = :categoryId)");
		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId).addValue("arrow", arrow);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	public List<Item> findByNameCategoryLarge(Integer arrow, String itemName, Integer categoryId) {// 商品名とカテゴリー大で検索します.
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE i.name LIKE :itemName AND split_part(c1.name_all, '/', 1) = (SELECT category_name FROM category WHERE id=:categoryId) ");
		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("categoryId", categoryId);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	public List<Item> findByNameCategoryMedium(Integer arrow, String itemName, Integer categoryId) {// 商品名と中カテゴリーで検索します.
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c1.name_all, '/', 1) || '/' || split_part(c1.name_all, '/', 2)) = (SELECT (c2.category_name || '/' || c1.category_name) FROM category c1 LEFT OUTER JOIN category c2 ON c1.parent_id = c2.id WHERE c1.id = :categoryId) ");
		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("categoryId", categoryId);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	public List<Item> findByNameCategorySmall(Integer arrow, String itemName, Integer categoryId) {// 商品名とカテゴリー小で検索します.
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c1.name_all, '/', 1) || '/' || split_part(c1.name_all, '/', 2) || '/' || split_part(c1.name_all, '/', 3)) = (SELECT name_all FROM category WHERE id = :categoryId)");
		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("categoryId", categoryId);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	public List<Item> findByCategoryLargeBrand(Integer arrow, Integer categoryId, String brand) {// 商品名とカテゴリー大で検索します.
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE split_part(c1.name_all, '/', 1) = (SELECT category_name FROM category WHERE id=:categoryId) AND i.brand LIKE :brand ");
		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("categoryId", categoryId).addValue("brand", brand);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	public List<Item> findByCategoryMediumBrand(Integer arrow, Integer categoryId, String brand) {// 商品名と中カテゴリーで検索します.
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE (split_part(c1.name_all, '/', 1) || '/' || split_part(c1.name_all, '/', 2)) = (SELECT (c2.category_name || '/' || c1.category_name) FROM category c1 LEFT OUTER JOIN category c2 ON c1.parent_id = c2.id WHERE c1.id = :categoryId) AND i.brand LIKE :brand ");
		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("categoryId", categoryId).addValue("brand", brand);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	public List<Item> findByCategorySmallBrand(Integer arrow, Integer categoryId, String brand) {// 商品名とカテゴリー小で検索します.
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE (split_part(c1.name_all, '/', 1) || '/' || split_part(c1.name_all, '/', 2) || '/' || split_part(c1.name_all, '/', 3)) = (SELECT name_all FROM category WHERE id = :categoryId) AND i.brand LIKE :brand ");
		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("categoryId", categoryId).addValue("brand", brand);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	public List<Item> findByNameCategoryLargeBrand(Integer arrow, String itemName, Integer categoryId, String brand) {// 商品名とカテゴリー大とブランド名で検索します.
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE i.name LIKE :itemName AND split_part(c1.name_all, '/', 1) = (SELECT category_name FROM category WHERE id=:categoryId) AND i.brand LIKE :brand ");
		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("categoryId", categoryId).addValue("brand", brand);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	public List<Item> findByNameCategoryMediumBrand(Integer arrow, String itemName, Integer categoryId, String brand) {// 商品名と中カテゴリーとブランド名で検索します.
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c1.name_all, '/', 1) || '/' || split_part(c1.name_all, '/', 2)) = (SELECT (c2.category_name || '/' || c1.category_name) FROM category c1 LEFT OUTER JOIN category c2 ON c1.parent_id = c2.id WHERE c1.id = :categoryId) AND i.brand LIKE :brand ");
		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("categoryId", categoryId).addValue("brand", brand);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}
	public List<Item> findByNameCategorySmallBrand(Integer arrow, String itemName, Integer categoryId, String brand) {// 商品名と小カテゴリーとブランド名で検索します.
		StringBuilder sql = new StringBuilder(getSQL());
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c1.name_all, '/', 1) || '/' || split_part(c1.name_all, '/', 2) || '/' || split_part(c1.name_all, '/', 3)) = (SELECT name_all FROM category WHERE id = :categoryId) AND i.brand LIKE :brand ");
		sql.append("ORDER BY i.id LIMIT 30 OFFSET :arrow;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("arrow", arrow).addValue("itemName", itemName).addValue("categoryId", categoryId).addValue("brand", brand);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_CATEGORY_ROW_MAPPER);
		return itemList;
	}


	public StringBuilder getCountSQL() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT (count(*) / 30) + 1 ");
		sql.append("FROM items i inner join category c on i.category_id = c.id ");
		return sql;
	}
	public StringBuilder getCountSQL2() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT (count(*) / 30) + 1 ");
		sql.append("FROM category c1 LEFT OUTER JOIN category c2 ON c1.parent_id = c2.id LEFT OUTER JOIN items i ON c1.id = i.category_id ");
		return sql;
	}


	public Integer countPage() {// 総ページ数を取得します
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT (count(id) / 30) + 1 FROM items;");
		SqlParameterSource param = new MapSqlParameterSource();
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	public Integer countPageName(String name) {// 商品名検索
		StringBuilder sql = new StringBuilder(getCountSQL());
		sql.append("WHERE i.name like :name;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	public Integer countPageBrand(String brand) {// ブランド名検索
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT (count(id) / 30) + 1 FROM items WHERE brand like :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	public Integer countPageNameBrand(String name, String brand) {// 商品名とブランド名検索
		StringBuilder sql = new StringBuilder(getCountSQL());
		sql.append("WHERE i.name LIKE :name AND i.brand LIKE :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name).addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}


	public Integer countPageLarge(Integer categoryId) {// 大カテゴリの総ページ数を取得
		StringBuilder sql = new StringBuilder(getCountSQL());
		sql.append("WHERE split_part(c.name_all, '/', 1) = (SELECT category_name FROM category WHERE id = :categoryId);");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	public Integer countPageMedium(Integer categoryId) {// 中カテゴリの総ページ数を取得
		StringBuilder sql = new StringBuilder(getCountSQL2());
		sql.append("WHERE (split_part(c1.name_all, '/', 1) || '/' || split_part(c1.name_all, '/', 2)) = (SELECT (c2.category_name || '/' || c1.category_name) FROM category c1 LEFT OUTER JOIN category c2 ON c1.parent_id = c2.id WHERE c1.id = :categoryId);");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	public Integer countPageSmall(Integer categoryId) {// 小カテゴリの総ページ数
		StringBuilder sql = new StringBuilder(getCountSQL());
		sql.append("WHERE (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2) || '/' || split_part(c.name_all, '/', 3)) = (SELECT name_all FROM category WHERE id = :categoryId);");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}


	public Integer countPageNameLarge(String itemName, Integer categoryId) {// 商品名と大カテゴリの総ページ数を取得
		StringBuilder sql = new StringBuilder(getCountSQL());
		sql.append("WHERE i.name LIKE :itemName AND split_part(c.name_all, '/', 1) = (SELECT category_name FROM category WHERE id = :categoryId);");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("categoryId", categoryId);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	public Integer countPageNameMedium(String itemName, Integer categoryId) {// 商品名と中カテゴリの総ページ数を取得
		StringBuilder sql = new StringBuilder(getCountSQL2());
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c1.name_all, '/', 1) || '/' || split_part(c1.name_all, '/', 2)) = (SELECT (c2.category_name || '/' || c1.category_name) FROM category c1 LEFT OUTER JOIN category c2 ON c1.parent_id = c2.id WHERE c1.id = :categoryId);");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("categoryId", categoryId);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	public Integer countPageNameSmall(String itemName, Integer categoryId) {// 商品名と小カテゴリの総ページ数を取得
		StringBuilder sql = new StringBuilder(getCountSQL());
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2) || '/' || split_part(c.name_all, '/', 3)) = (SELECT name_all FROM category WHERE id = :categoryId);");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("categoryId", categoryId);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}


	public Integer countPageLargeBrand(Integer categoryId, String brand) {// 大カテゴリとブランド名の総ページ数を取得
		StringBuilder sql = new StringBuilder(getCountSQL());
		sql.append("WHERE split_part(c.name_all, '/', 1) = (SELECT category_name FROM category WHERE id = :categoryId) AND i.brand LIKE :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId).addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	public Integer countPageMediumBrand(Integer categoryId, String brand) {// 中カテゴリとブランド名の総ページ数を取得
		StringBuilder sql = new StringBuilder(getCountSQL2());
		sql.append("WHERE (split_part(c1.name_all, '/', 1) || '/' || split_part(c1.name_all, '/', 2)) = (SELECT (c2.category_name || '/' || c1.category_name) FROM category c1 LEFT OUTER JOIN category c2 ON c1.parent_id = c2.id WHERE c1.id = :categoryId) AND i.brand LIKE :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId).addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	public Integer countPageSmallBrand(Integer categoryId, String brand) {// 小カテゴリとブランド名の総ページ数を取得
		StringBuilder sql = new StringBuilder(getCountSQL());
		sql.append("WHERE (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2) || '/' || split_part(c.name_all, '/', 3)) = (SELECT name_all FROM category WHERE id = :categoryId) AND i.brand LIKE :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId).addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}


	public Integer countPageNameLargeBrand(String itemName, Integer categoryId, String brand) {// 商品名と大カテゴリとブランド名の総ページ数を取得
		StringBuilder sql = new StringBuilder(getCountSQL());
		sql.append("WHERE i.name LIKE :itemName AND split_part(c.name_all, '/', 1) = (SELECT category_name FROM category WHERE id = :categoryId) AND i.brand LIKE :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("categoryId", categoryId).addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	public Integer countPageNameMediumBrand(String itemName, Integer categoryId, String brand) {// 商品名と中カテゴリとブランド名の総ページ数を取得
		StringBuilder sql = new StringBuilder(getCountSQL2());
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c1.name_all, '/', 1) || '/' || split_part(c1.name_all, '/', 2)) = (SELECT (c2.category_name || '/' || c1.category_name) FROM category c1 LEFT OUTER JOIN category c2 ON c1.parent_id = c2.id WHERE c1.id = :categoryId) AND i.brand LIKE :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("categoryId", categoryId).addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}
	public Integer countPageNameSmallBrand(String itemName, Integer categoryId, String brand) {// 商品名と小カテゴリとブランド名の総ページ数を取得
		StringBuilder sql = new StringBuilder(getCountSQL());
		sql.append("WHERE i.name LIKE :itemName AND (split_part(c.name_all, '/', 1) || '/' || split_part(c.name_all, '/', 2) || '/' || split_part(c.name_all, '/', 3)) = (SELECT name_all FROM category WHERE id = :categoryId) AND i.brand LIKE :brand;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", itemName).addValue("categoryId", categoryId).addValue("brand", brand);
		return template.queryForObject(sql.toString(), param, Integer.class);
	}

	public StringBuilder getSQL2() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, parent_id, category_name, name_all FROM category ");
		return sql;
	}
	public List<CategoryName> categoryLargeText() {// 大カテゴリネーム取得
		StringBuilder sql = new StringBuilder(getSQL2());
		sql.append("WHERE parent_id IS NULL AND name_all IS NULL;");
		List<CategoryName> categoryLargeList = template.query(sql.toString(), CATEGORY_NAME_ROW_MAPPER);
		return categoryLargeList;
	}
	public List<CategoryName> categoryMediumText() {// 中カテゴリネーム取得
		StringBuilder sql = new StringBuilder(getSQL2());
		sql.append("WHERE name_all IS NULL AND parent_id IS NOT NULL;");
		List<CategoryName> categoryLargeList = template.query(sql.toString(), CATEGORY_NAME_ROW_MAPPER);
		return categoryLargeList;
	}
	public List<CategoryName> categorySmallText() {// 小カテゴリネーム取得
		StringBuilder sql = new StringBuilder(getSQL2());
		sql.append("WHERE parent_id IS NOT NULL AND name_all IS NOT NULL;");
		List<CategoryName> categoryLargeList = template.query(sql.toString(), CATEGORY_NAME_ROW_MAPPER);
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