package com.interfaces.services;

import com.entities.User;

public interface UserService {

	/**
	 * ユーザー情報を登録する処理
	 * @param user 保存するユーザー
	 * @param rawPassword パスワード
	 */
	void register(User user, String rawPassword);
}
