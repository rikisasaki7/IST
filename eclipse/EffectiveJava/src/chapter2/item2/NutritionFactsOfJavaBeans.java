/**
 * 
 */
package chapter2.item2;

/**
 * @author Riki
 * JavaBeansパターン
 * 不整合を許し、可変性を必要とする
 * しかし、生成途中で不整合な状態にあるかもしれない。
 */
public class NutritionFactsOfJavaBeans {

	// (あれば)デフォルト値でパラメータを初期化する
	private int servingSize = -1; // (ml) 必須、デフォルト値はない
	private int servings = -1; // (容器当たり) 必須、デフォルト値はない
	private int calories; // オプション
	private int fat; // （g） オプション
	private int sodium; // （mg） オプション
	private int carbohydrate; // （g）オプション
	
	/**
	 * JavaBeanパターンを使用したコンストラクタとアクセサ群
	 */
	public NutritionFactsOfJavaBeans(){}

	/**
	 * @param servingSize
	 */
	public void setServingSize(int servingSize) {
		this.servingSize = servingSize;
	}

	/**
	 * @param servings
	 */
	public void setServings(int servings) {
		this.servings = servings;
	}

	/**
	 * @param calories
	 */
	public void setCalories(int calories) {
		this.calories = calories;
	}

	/**
	 * @param fat
	 */
	public void setFat(int fat) {
		this.fat = fat;
	}

	/**
	 * @param sodium
	 */
	public void setSodium(int sodium) {
		this.sodium = sodium;
	}

	/**
	 * @param carbohydrate
	 */
	public void setCarbohydrate(int carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// インスタンス生成
		NutritionFactsOfJavaBeans nf = new NutritionFactsOfJavaBeans();
		nf.setServingSize(20);
		nf.setServings(30);
		nf.setCalories(1000);
		nf.setFat(0);
		nf.setSodium(140);
		nf.setCarbohydrate(60);
	}
}
