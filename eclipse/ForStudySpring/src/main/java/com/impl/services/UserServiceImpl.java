/**
 * 
 */
package com.impl.services;

import com.entities.User;
import com.interfaces.repositories.UserRepository;
import com.interfaces.services.UserService;
import com.interfaces.utilities.PasswordEncoder;

/**
 * @author Riki
 *
 */
public class UserServiceImpl implements UserService {
	
	@SuppressWarnings("unused")
	private UserRepository userRepository;
	@SuppressWarnings("unused")
	private PasswordEncoder passwordEncoder;
	
	/**
	 * @param userRepository
	 * @param passwordEncoder
	 */
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = encoder;
	}
	
	/**
	 * ユーザー情報を登録する処理
	 * @param user 保存するユーザー
	 * @param rawPassword パスワード
	 */
	public void register(User user, String rawPassword){
		// Userの登録処理を記述
		return;
	}

	/** 各クラスの結合度が高く、好ましくない例 */
//	/**
//	 * コンストラクタ
//	 * @param dataSource データソース
//	 */
//	public UserServiceImpl(DataSource dataSource){
//		this.userRepository = new JdbcUserRepository(dataSource);
//		this.passwordEncoder = new BCryptPasswordEncoder();
//	}
//	
//	public void register(User user, String rawPassword){
//		if(this.userRepository.countByUsername(user.getUsername()) > 0){
//			//ユーザー名がすでに使用されていたら例外をスローする
//			throw new UserAlreadyRegisteredException();
//		}
//		// 生パスワードをハッシュ化して設定
//		user.setPassword(this.passwordEncoder.encode(rawPassword));
//		this.userRepository.save(user);
//	}

}
