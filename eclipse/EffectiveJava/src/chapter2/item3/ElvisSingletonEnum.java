package chapter2.item3;

/**
 * @author Riki
 * シングルトン特性を保持する一番好ましい方法
 * Enum定義をする
 * ディシリアライズ時にもシングルトンを守る
 * リフレクションをされてもシングルトンを守る
 */
public enum ElvisSingletonEnum {
	INSTANCE,
	ELVIS,
	HARRY,
	FUNNNNNN;
}
