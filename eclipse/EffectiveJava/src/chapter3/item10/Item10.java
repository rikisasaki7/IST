package chapter3.item10;

/**
 * @author Riki
 * 項目１０
 * toStringを常にオーバーライドする
 * ドキュメンテーションとして戻り値の形式を明示するかは重要　
 * ー＞　明示するとクラスを利用するクライアントが利用する
 * ー＞　その分、一度配布したら変更はできなくなる。
 * ー＞　ドキュメントに記載しなければ変更できる。
 * 特に電話番号など、値を持つ値クラスの時にはオーバーラードが役にたつ
 * しかし、形式を明示するしないに関わらず、toStringメソッドの意図は明示すべき
 * 
 * なお、形式を明示するしないに関わらず、toStringで返される値に含まれる
 * 全ての情報へのプログラミングによるアクセスを手段を提供すること。
 * そうしないと、クライアントにtoStringの解析を強制することになる。
 */
public class Item10 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
/*
 * 項目９のPhoneNumberクラス
 */
class PhoneNumber {
	/** 市外局番 */
	private final short areaCode;
	/** 市内局番 */
	private final short prefix;
	/** 加入者番号 */
	private final short lineNumber;
	
	/**
	 * デフォルトコンストラクタ
	 * インスタンス生成前に番号の範囲妥当性チェックを行います。
	 * @param areaCode 市外局番
	 * @param prefix 市内局番
	 * @param lineNumber 加入者番号
	 */
	public PhoneNumber(int areaCode, int prefix, int lineNumber) {
		this.areaCode = (short)areaCode;
		this.prefix = (short)prefix;
		this.lineNumber = (short)lineNumber;
	}
	
	/*
	 * ドキュメントによるtoStringメソッドの形式を明示した意図説明の例
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	 * この電話番号の文字列表現を返します。
	 * 文字列は、１４文字で構成されて、その形式は”（XXX） YYY-ZZZZ”です。
	 * XXXは市外局番で、YYY-ZZZZは市内局番です。
	 * （各大文字は、１桁の数字を表しています。）
	 * 
	 * この電話番号の３つの部分のどれかが、そのフィールドを埋めるには
	 * 桁が少ない場合には、そのフィールドが０で埋められます。
	 * 例えば、最後の４桁部分の番号が１２３だとしたら、文字列表現最後の４文字は”０１２３”になります。
	 * 
	 * 市外局番の括弧の後に、市内局番と区切るための空白が１つあることに
	 * 注意してください。
	 * 
	 * ドキュメントによるtoStringメソッドの形式を明示しない意図説明の例
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	 * この妙薬の簡潔な説明を返します。その表現の正確な詳細は明示されませんし、
	 * 変更されることがあります。しかし、次の形式が普通でしょう。
	 * "[妙薬 #9: 種類＝恋愛、香り=テレビン油、外観=墨（色）]"
	 */
	@Override
	public String toString() {
		return String.format("(%03d) %03d-%04d", areaCode, prefix, lineNumber);
	}

	/**
	 * @return 市外局番
	 */
	public short getAreaCode() {
		return areaCode;
	}

	/**
	 * @return 市内局番
	 */
	public short getPrefix() {
		return prefix;
	}

	/**
	 * @return 加入者番号
	 */
	public short getLineNumber() {
		return lineNumber;
	}
}