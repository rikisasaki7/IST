package chapter3.item12;

/*
 * 項目１２
 * Comparableの実装を検討する
 */
public final class CaseInsensitiveString implements Comparable<CaseInsensitiveString> {

	private final String s;
	public CaseInsensitiveString(String s){
		if(s == null) throw new NullPointerException();
		this.s = s;
	}

	/*
	 * compareToの実装
	 * パラメータがCaseInsensitiveStringである事に注目
	 * これでCaseInsensitiveString同士の比較しかできない。
	 */
	@Override
	public int compareTo(CaseInsensitiveString cis){
		return String.CASE_INSENSITIVE_ORDER.compare(s, cis.s);
	}

	public static void main(String args[]){
		// ↑で作ったのテスト
		PhoneNumber pn1 = new PhoneNumber(3, 1111, 2222);
		PhoneNumber pn2 = new PhoneNumber(3, 1112, 3333);
		PhoneNumber pn3 = new PhoneNumber(3, 1111, 1333);

		System.out.println(pn1.compareTo(pn2));
		System.out.println(pn2.compareTo(pn3));
		System.out.println(pn1.compareTo(pn3));
		System.out.println(pn1.compareTo(pn1));
	}


}

class PhoneNumber implements Comparable<PhoneNumber>{
	/** 市外局番 */
	private final short areaCode;
	/** 市内局番 */
	private final short prefix;
	/** 加入者番号 */
	private final short lineNumber;

	/**
	 * デフォルトコンストラクタ
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
	 * compareToの実装
	 * 比較は意味のあるフィールドからはじめ、意味が弱くなる順番になるよう実施する。
	 */
	@Override
	public int compareTo(PhoneNumber pn){
		// 市外局番を比較する
		if(areaCode < pn.areaCode) return -1;
		if(areaCode > pn.areaCode) return 1;

		// 市外局番は等しく、市内局番の前半を比較する
		if(prefix < pn.prefix) return -1;
		if(prefix > pn.prefix) return 1;

		// 市外局番と市内局番の前半は等しく、市内局番の後半を比較する。
		if(lineNumber < pn.lineNumber) return -1;
		if(lineNumber > pn.lineNumber) return 1;

		return 0; // 全てのフィールドが等しい

	}

//	/*
//	 * compareToの実装　その２
//	 * こうすれば多少早くできるが、intがオーバフローする可能性がある。
//	 */
//	public int compareTo(PhoneNumber pn){
//		// 市外局番を比較する
//		int areaCodeDiff = areaCode - pn.areaCode;
//		if(areaCodeDiff != 0) return areaCodeDiff;
//
//		// 市外局番は等しく、市内局番の前半を比較する
//		int prefixDiff = prefix - pn.prefix;
//		if(prefixDiff != 0) return prefixDiff;
//
//		// 市外局番と市内局番の前半は等しく、市内局番の後半を比較する。
//		return lineNumber - pn.lineNumber;
//
//	}

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