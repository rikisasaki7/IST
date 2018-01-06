package chapter3.item8;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Riki
 * 項目８
 * equalsをオーバーライドするときは一般契約に従う
 * 一般契約：
 * １．反射的
 * ２．対称的　＜－このクラスではこれ
 * ３．推移的
 * ４．整合的
 * ５．非Null性
 */
public final class CaseInsensitiveString {

	private final String s;
	public CaseInsensitiveString(String s){
		if(s == null) throw new NullPointerException();
		this.s = s;
	}
//	/*
//	 * 対称性を守っていないequalsメソッド
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object o){
//		if(o instanceof CaseInsensitiveString)
//			return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
//		if(o instanceof String) // 一方向の相互作用
//			return s.equalsIgnoreCase((String) o);
//		return false;
//	}

	/*
	 * 対称性を守っているequalsメソッド
	 * Stringに対する記述を削除。
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o){
		return o instanceof CaseInsensitiveString &&
				((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
	}

	/*
	 * mainメソッド
	 */
	public static void main(String[] args){

		CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
		CaseInsensitiveString cis2 = new CaseInsensitiveString("Polish");
		CaseInsensitiveString cis3 = new CaseInsensitiveString("polish");
		String s = "polish";
		System.out.println(cis.equals(s)); // s.equals(cis)がfalseを返すので、falseを返す必要がある⇒対称性
		System.out.println(s.equals(cis)); // Stringのequalsは大文字小文字を区別するので、falseを返す
		System.out.println(cis.equals(cis2));
		System.out.println(cis.equals(cis3));
		List<CaseInsensitiveString> list = new ArrayList<CaseInsensitiveString>();
		list.add(cis);
		System.out.println(list.contains(s));

	}
}
