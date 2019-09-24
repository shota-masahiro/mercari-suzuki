package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * usersテーブルを操作するリポジトリ.
 * 
 * @author shota.suzuki
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 登録処理をします.
	 * 
	 * @param user ユーザ情報
	 */
	public void insert(User user) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO users(name, mail_address, password) ");
		sql.append("VALUES(:name, :mailAddress, :password);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql.toString(), param);
	}

}
