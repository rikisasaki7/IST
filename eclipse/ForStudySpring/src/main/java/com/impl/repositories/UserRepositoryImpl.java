/**
 * 
 */
package com.impl.repositories;

import com.entities.User;
import com.interfaces.repositories.UserRepository;

/**
 * @author Riki
 *
 */
public class UserRepositoryImpl implements UserRepository {

	/**
	 * ユーザー情報を永続化層に保存する
	 * @param user 保存するユーザー
	 * @return 永続化したユーザー
	 */
	public User save(User user){
		return user;
	}
	
	/**
	 * ユーザー数をカウントする
	 * @param username 保存するユーザー
	 * @return ユーザー数
	 */
	public int countByUsername(String username){
		return 0;
	}
}
