package com.example.domain;

/**
 * ユーザを表すドメイン.
 * 
 * @author shota.suzuki
 *
 */
public class User {
	
	/** ID */
	private Integer id;
	
	/** 名前 */
	private String name;
	
	/** パスワード */
	private String password;
	
	/** 権限 */
	private Integer authority;


	public User() {

	}


	public User(Integer id, String name, String password, Integer authority) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.authority = authority;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getAuthority() {
		return authority;
	}
	public void setAuthority(Integer authority) {
		this.authority = authority;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", authority=" + authority + "]";
	}

}
