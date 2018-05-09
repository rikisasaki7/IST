package chapter4.item19;

import static chapter4.item19.PhysicalConstants.*;

/*
 * 項目１９
 * 型を定義するためだけにインタフェースを使用する
 */
public class Test {
	double atoms(double mols){
		// staticインポートするとこんな感じ
		return AVOGADROS_NUMBER * mols;
	}

}
