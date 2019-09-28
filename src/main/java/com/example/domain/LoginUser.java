package com.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * ユーザのログイン情報を格納するエンティティ.
 * 
 * @author shota.suzuki
 *
 */
public class LoginUser extends User {
	
	private static final long serialVersionUID = 1L;
	
	/** ユーザ */
	private final com.example.domain.User user;
	
	/**
	 * ユーザ情報と認可用ロールを設定します.
	 * 
	 * @param user          ユーザ情報
	 * @param authorityList 権限情報が入ったリスト
	 */
	public LoginUser(com.example.domain.User user, Collection<GrantedAuthority> authorityList) {
		super(user.getEmail(), user.getPassword(), authorityList);
		this.user = user;
	}
	
	
	public com.example.domain.User getUser() {
		return user;
	}

}