package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.TestItem;

/**
 * 動作確認用
 * 
 * @author shota.suzuki
 *
 */
@Repository
public class TestRepository {

	private static final RowMapper<TestItem> ROW_MAPPER = new BeanPropertyRowMapper<>(TestItem.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	//検索の実行メソッド
	public List<TestItem> search(Integer arrow, String itemName, String nameAll, String brand) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String sql = createSQL(arrow, itemName, nameAll, brand, params);
		return template.query(sql, params, ROW_MAPPER);
	}

	//SQLを整形する
	private String createSQL(Integer arrow, String itemName, String nameAll, String brand, MapSqlParameterSource params) {
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
		if (true) {
			sql.append("AND name_all LIKE :nameAll");
			params.addValue("nameAll", nameAll+"%");
		}
		
		//ブランド名
		if (brand != null) {
			sql.append("AND brand LIKE :brand ");
			params.addValue("brand", brand);
		}

		sql.append("ORDER BY i.id ");
		sql.append("LIMIT 30 OFFSET :arrow;");
		params.addValue("arrow", arrow);

		System.out.println("SQL="+sql.toString());

		return sql.toString();
	}

}