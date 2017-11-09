/**
 * 
 */
package chapter2.Item1;

/**
 * @author Riki
 * staticファクトリメソッドの例
 * サービスプロバイダーフレームワークのスケッチ
 * サービスプロバイダー
 */
public interface Provider {
	
	// 新しいServiceインスタンスを生成して返却する
	Service newService();
	// パラメータで指定したServiceインスタンスを返却する。
	Service getService(String s);
}
