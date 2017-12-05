/**
 * 
 */
package com.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.impl.repositories.UserRepositoryImpl;
import com.impl.services.UserServiceImpl;
import com.impl.utilities.BCryptPasswordEncoder;
import com.interfaces.repositories.UserRepository;
import com.interfaces.services.UserService;
import com.interfaces.utilities.PasswordEncoder;

// TODO 2.1.3 Configuration方法 JavaベースConfiguration
/**
 * Javaベースコンフィギュレーションを試します。
 * Configurationアノテーションを付与することにより、コンフィギュレーションクラスとして認識されます。
 * Beanアノテーションを付与し、Bean定義を記載します。
 * Bean名を明示する場合は「@Bean(name="userRepo")」のようにname属性を使用します。
 * @author Riki
 *
 */
@Configuration
public class AppConfig {
	
	/**
	 * JavaベースConfiguration。@Beanを指定。
	 * Beanの定義を行う。
	 * メソッド名がBean名、戻り値がインジェクションされるインスタンスとなる。
	 * @author Riki
	 */
	@Bean
	UserRepository userRepository(){
		return new UserRepositoryImpl();
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	UserService userService(){
//		return new UserServiceImpl(userRepository(), passwordEncoder());
//	}
	// 
	/**
	 * 他のコンポーネントを参照する場合、こっち。↑の引数なしでも問題なし。
	 * @author Riki
	 * @param userRepository
	 * @param passwordEncoder
	 * @return UserServiceImpl
	 */
	@Bean
	UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder){
		return new UserServiceImpl(userRepository, passwordEncoder);
	}
}
