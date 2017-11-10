/**
 * 
 */
package item2;

/**
 * @author Riki
 * テレスコーピングコンストラクタパターン
 * これだとコンストラクタパラメータが多くてうまく対応できない
 */
public class NutritionFactsOfTelescoping {
	private final int servingSize; // (ml) 必須
	private final int servings; // (容器当たり) 必須
	private final int calories; // オプション
	private final int fat; // （g） オプション
	private final int sodium; // （mg） オプション
	private final int carbohydrate; // （g）オプション
	
	/** 
	 * テレスコーピングコンストラクタパターンを使用したコンストラクタ群
	 * @param servingSize 必須パラメータ
	 * @param servings 必須パラメータ
	 * @return オプションパラメータに０が設定された自分自身のインスタンス
	 */
	public NutritionFactsOfTelescoping(int servingSize, int servings){
		this(servingSize, servings, 0, 0, 0, 0);
	}
	public NutritionFactsOfTelescoping(int servingSize, int servings, int calories){
		this(servingSize, servings, calories, 0, 0, 0);
	}
	public NutritionFactsOfTelescoping(int servingSize, int servings, int calories, int fat){
		this(servingSize, servings, calories, fat, 0, 0);
	}
	public NutritionFactsOfTelescoping(int servingSize, int servings, int calories, int fat, int sodium){
		this(servingSize, servings, calories, fat, sodium, 0);
	}
	public NutritionFactsOfTelescoping(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate){
		this.servingSize = servingSize;
		this.servings = servings;
		this.calories = calories;
		this.fat = fat;
		this.sodium = sodium;
		this.carbohydrate = carbohydrate;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// インストラクタ生成
		// 途中に０を設定してしまっている
		NutritionFactsOfTelescoping nf = new NutritionFactsOfTelescoping(10, 200, 400, 500, 0, 800);
		System.out.println(nf);

	}
}
