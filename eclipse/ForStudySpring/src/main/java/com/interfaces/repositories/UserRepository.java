package com.interfaces.repositories;

import com.entities.User;

public interface UserRepository {
	
	/**
	 * ユーザー情報を永続化層に保存する
	 * @param user 保存するユーザー
	 * @return 永続化したユーザー
	 */
	User save(User user);

	/**
	 * ユーザー数をカウントする
	 * @param username 保存するユーザー
	 * @return ユーザー数
	 */
	int countByUsername(String username);

}
