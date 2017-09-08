package com.entities;

public class User {
	
	/* ユーザー名 */
	private String username;
	/* パスワード */
	private String password;

	/**
	 * @return ユーザー名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username セットする ユーザー名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password セットする パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
