package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.TestItem;
import com.example.domain.TestNameAll;

/**
 * 動作確認用
 * 
 * @author shota.suzuki
 *
 */
@Repository
public class TestRepository {

	private static final RowMapper<TestItem> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(TestItem.class);
	private static final RowMapper<TestNameAll> NAME_ALL_ROW_MAPPE = new BeanPropertyRowMapper<>(TestNameAll.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	//検索実行用のメソッド カテゴリー大中小の値を取得
	public TestNameAll searchName(Integer[] categoryIds) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		if (categoryIds[0] == null) {
			return null;
		}
		String sql = createNameAllSQL(categoryIds, params);
		return template.queryForObject(sql, params, NAME_ALL_ROW_MAPPE);
	}

	//SQLを整形する
	private String createNameAllSQL(Integer[] categoryIds, MapSqlParameterSource params) {
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
			sql.append("WHERE c1.id=:largeId LIMIT 1 OFFSET 0;");
			params.addValue("largeId", categoryIds[0]);
		}

		return sql.toString();
	}


	//検索の実行メソッド 商品一覧を取得する
	public List<TestItem> search(Integer arrow, String itemName, TestNameAll nameAll, String brand, String countPage) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = createSQL(arrow, itemName, nameAll, brand, countPage, params);
		return template.query(sql, params, ITEM_ROW_MAPPER);
	}

	//検索の実行メソッド 商品数を取得する
	public Integer searchCount(Integer arrow, String itemName, TestNameAll nameAll, String brand, String countPage) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = createSQL(arrow, itemName, nameAll, brand, countPage, params);
		return template.queryForObject(sql, params, Integer.class);
	}

	//SQLを整形する
	private String createSQL(Integer arrow, String itemName, TestNameAll nameAll, String brand, String countPage, MapSqlParameterSource params) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT i.id as id, i.name as name, condition, category_id, brand, price, shipping, description, name_all ");
		sql.append("FROM items i LEFT OUTER JOIN category c on c.id = i.category_id ");
		sql.append("WHERE 1=1 ");

		// 商品名(あいまい検索)
		if (itemName != null) {
			sql.append("AND i.name LIKE :itemName ");
			params.addValue("itemName", "%"+itemName+"%");
		}

		//カテゴリー名
		if (nameAll != null) {
			if (nameAll.getSmallName() != null) {
				sql.append("AND name_all LIKE :nameAll ");
				params.addValue("nameAll", nameAll.getSmallName()+"%");
			} else if (nameAll.getMidiumName() != null) {
				sql.append("AND name_all LIKE :nameAll ");
				params.addValue("nameAll", nameAll.getMidiumName()+"%");
			}
			sql.append("AND name_all LIKE :nameAll ");
			params.addValue("nameAll", nameAll.getLargeName()+"%");

		}

		//ブランド名
		if (brand != null) {
			sql.append("AND brand LIKE :brand ");
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

}