/**
 * 
 */
package chapter2.item2;

/**
 * @author Riki
 * ビルダーパターンを使用
 */
public class NutritionFactsOfBuilder extends Parent {
	
	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;
	
	// publicでstaticなインナークラス
	// Builderインタフェースを実装
	// 型パラメータを持つBuilderインタフェースは、各BuilderのためのAbstract Factoryとなる
	public static class ProcessedFoodsBuilder implements Builder<NutritionFactsOfBuilder>{
		
		// 必須パラメータ
		private final int servingSize;
		private final int servings;
		
		// オプションパラメータ デフォルト値に初期化
		private int calories = 0;
		private int fat = 0;
		private int carbohydrate = 0;
		private int sodium = 0;
		
		// 必須パラメータを指定したコンストラクタ
		public ProcessedFoodsBuilder(int servingSize, int servings){
			this.servingSize = servingSize;
			this.servings = servings;
		}
		
		// メソッドチェーンを使用できるオプションパラメータ用setter
		public ProcessedFoodsBuilder calories(int val){
			calories = val;
			return this;
		}
		public ProcessedFoodsBuilder fat(int val){
			fat = val;
			return this;
		}
		public ProcessedFoodsBuilder carbohydrate(int val){
			carbohydrate = val;
			return this;
		}
		public ProcessedFoodsBuilder sodium(int val){
			sodium = val;
			return this;
		}
		
		// 最終的にインスタンスを生成するbuildメソッド
		public NutritionFactsOfBuilder build(){
			NutritionFactsOfBuilder tmp = new NutritionFactsOfBuilder(this);
			// 検証するときは、Builderからインスタンスのフィールドへ写した後にするべき
			if(checkPoisonal(tmp)){
				// 検証結果NGの場合は、例外を出すべき
				throw new IllegalStateException();
			}
			return tmp;
		}

		// 検査用メソッド
		private boolean checkPoisonal(NutritionFactsOfBuilder targetObject){
			boolean ret = false;
			if(targetObject.calories >= 1000) ret = true;
			return ret;
		}

		// クライアントが作ったBuilderを元に、インスタンスを生成したい場合
		// 上では引数なしのbuildメソッドを用意しているが、境界ワイルドカードを使用し、使用するビルダーを制限できる
//		public Child buildChile(Builder<? extends Parent> parentBuilder){
//			return new Child(parentBuilder);
//		}
//		private Child(Builder<? extends Parent> parentBuilder){
//			this.age = parentBuilder.age;
//			this.sex = parentBuilder.sex;
//		}
	}
	
	// ビルダークラスからのみ呼ばれるprivateのコンストラクタ
	// Builderを引数とし、builderにセットされた値をそれぞれ親クラスに設定
	private NutritionFactsOfBuilder(ProcessedFoodsBuilder builder){
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
	}
	
}

class Parent{}