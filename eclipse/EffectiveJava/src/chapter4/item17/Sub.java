package chapter4.item17;

import java.util.Date;

/*
 * 項目１７
 * 継承のために設計および文書化する、でなければ継承を禁止する
 */
public final class Sub extends Super{
	private final Date date; // コンストラクタにより設定されるブランクfinal

	public Sub(){
		date = new Date();
	}

	/* アクセッサーメソッド */
	public Date getDate() {
		return date;
	}

	/*
	 * スーパークラスのコンストラクタから呼び出されるオーバーライドしているメソッド
	 */
	@Override
	public void overrideMe(){
		System.out.println(date);
	}

	/* メインメソッド */
	public static void main(String[] args){
		Sub sub = new Sub();
		sub.overrideMe();
	}

	// 実行結果は以下のようになる
	// null
	// Fri Apr 27 23:47:56 JST 2018
	// スーパークラスのコンストラクタはサブクラスのコンストラクタの前に呼ばれる
	// dateが初期化されるのはサブクラスのコンストラクタが実行されるとき
	// そのため、スーパークラスのコンストラクタ時には、サブクラスのoverrideMeが呼ばれはするが、
	// フィールドは初期化されていない



}
