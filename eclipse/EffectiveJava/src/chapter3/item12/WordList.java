package chapter3.item12;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/*
 * 項目１２
 * Comparableの実装を検討する
 * Comparableを実装することで、インスタンスが自然な順序を持っている事をクラスは表すこととなる。
 * Comparableインタフェースの一般契約は以下を参照（記述するのめんどい）
 * http://www.thekingsmuseum.info/entry/2015/09/04/003117
 *
 * まとめると、反射性、対称性、推移性を満たし、可能な限り比較の結果が０の場合はequalsでtrueにするべき
 * ー＞equalsが守られている場合、順序はequalsと一致しているといわれ、破られていれば順序はequalsと矛盾しているといわれる
 *
 * 加えて、同じクラスの比較のみをサポートし、ことなるクラスの比較時にはClassCastExceptionをthrowするべき
 * 上記一般契約を破っている場合は、一般契約に従って実装されている
 * 　ArraysやCollections、ソートリストのTreeMapやTreeSetが機能しなくなることに注意（それらはequalsと一致している前提で作られているから）
 */
public class WordList {

	public static void main(String args[]){

		// StringがComparableを実装している事に依存している
		// コマンドラインの引数の重複を取り除いたアルファベット順のリストを表示する
		// ここ参照
		// https://docs.oracle.com/javase/jp/8/docs/api/java/util/TreeSet.html]
		Set<String> s = new TreeSet<String>();
		Collections.addAll(s, args);
		System.out.println(s);
	}

}
