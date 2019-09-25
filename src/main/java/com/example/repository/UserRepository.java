package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
	 * userオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		user.setAuthority(rs.getInt("password"));
		return user;
	};
	
	
	/**
	 * user情報を取得します.
	 * 
	 * @param mailAddress メールアドレス
	 * @return            userオブジェクト
	 */
	public User findByMailAddress(String mailAddress) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, mail_address, password, authority ");
		sql.append("FROM users ");
		sql.append("WHERE mail_address=:mailAddress;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
		List<User> userList = template.query(sql.toString(), param, USER_ROW_MAPPER);
		if (userList.size() != 0) {
			return userList.get(0);
		} else {
			return null;
		}
		
	}

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
