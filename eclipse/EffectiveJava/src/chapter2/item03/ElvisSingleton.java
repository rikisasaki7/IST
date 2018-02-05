package chapter2.item03;

/**
 * @author Riki
 * 項目３
 * privateのコンストラクタかenum型でシングルトン特性を強制する
 * シングルトンの実装その１
 */
public class ElvisSingleton {

	/** 
	 * publicフィールドによるシングルトン
	 * 問題点：
	 * AccessibleObject.setAccesibleメソッドを使用してリフレクションにより
	 * 呼ばれてしまう可能性がある。
	 * ※リフレクション：
	 * ・文字列で指定されたクラスのメソッドを実行すること
	 * ・プログラムの実行過程でプログラム自身の構造を読み取ったり書き換えたりする技術のことである
	 * https://www.gixo.jp/blog/6602/
	 */
	public static final ElvisSingleton INSTANCE = new ElvisSingleton();
	/** 
	 * privateコンストラクタ
	 * INSTANCEインスタンスを初期化する際に一度だけ呼ばれる
	 */
	private ElvisSingleton(){}
	
}