package com.interfaces.utilities;

public interface PasswordEncoder {
	
	/**
	 * パスワードをハッシュ化する処理
	 * @param rawPassword ハッシュ対象のパスワード
	 * @return ハッシュ化したパスワード
	 */
	String encode(String rawPassword);

}
