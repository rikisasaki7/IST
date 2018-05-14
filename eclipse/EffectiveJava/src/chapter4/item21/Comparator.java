package chapter4.item21;

/*
 * 項目２１
 * 戦略を表現するために関数オブジェクトを使用する
 *
 * 様々な型で<code>StringLengthComparator</code>のコンパレータを使用できるようにするため、
 * Comparatorインタフェースを定義。
 * 具象戦略クラスと一緒に使うので、戦略インタフェースと呼ぶ
 */
public interface Comparator<T> {

	public int compare(T t1, T t2);

}
