/**
 * 
 */
package chapter2.item2;

/**
 * @author Riki
 * 項目２
 * 数多くのコンストラクタパラメータに直面した時にはビルダーを検討する
 */
public class Item2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// builderパターンを使用したインスタンス生成
		NutritionFactsOfBuilder cocaCola = new NutritionFactsOfBuilder.ProcessedFoodsBuilder(240, 500).
				calories(100).
				sodium(200).
				carbohydrate(100).build();
		System.out.println(cocaCola);
	}

}
