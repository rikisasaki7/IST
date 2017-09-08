/**
 * 
 */
package com.impl.utilities;

import com.interfaces.utilities.PasswordEncoder;

/**
 * @author Riki
 *
 */
public class BCryptPasswordEncoder implements PasswordEncoder {

	/**
	 * パスワードをハッシュ化する処理
	 * @param rawPassword ハッシュ対象のパスワード
	 * @return ハッシュ化したパスワード
	 */
	public String encode(String rawPassword){
		
		// ハッシュ化する処理を記述
		String hashedPassword = rawPassword;
		return hashedPassword;
	}

}
