/**
 * 
 */
package chapter2.item1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Riki
 * staticファクトリメソッドの例
 * サービスプロバイダーフレームワークのスケッチ
 * サービス登録とアクセスのためのインスタンス化不可能クラス
 */
public class Services {
	// インスタンス化を自制
	private Services(){}
	
	/** サービス名をサービスと対応づける */
	private static final Map<String, Provider> providers = 
			new ConcurrentHashMap<String, Provider>();
	public static final String DEFAULT_PROVIDER_NAME = "<def>";
	public static final String DEFAULT_PROVIDER_SOMESTRING = "<sasaki>";
	/** デフォルトのプロバイダー */
	public static final TestProvider DEFAULT_TEST_PROVIDER = new TestProvider();
	/** デフォルトのプロバイダーをstaticイニシャライザで登録 */
	
	// プロバイダー登録API
	public static void registerDefaultProvider(){
		registerProvider(DEFAULT_PROVIDER_NAME, DEFAULT_TEST_PROVIDER);
	}
	public static void registerProvider(String name, Provider p){
		providers.put(name, p);
	}
	
	// サービスアクセスAPI
	public static Service newInstance(){
		return newInstance(DEFAULT_PROVIDER_NAME);
	}
	public static Service newInstance(String name){
		Provider p = providers.get(name);
		if(p==null){
			throw new IllegalArgumentException(
					"No provider registererd with name: " + name);
		}
		return p.newService();
	}
	
	// サービスアクセスAPI
	public static Service getInstance(){
		return getInstance(DEFAULT_PROVIDER_NAME, DEFAULT_PROVIDER_SOMESTRING);
	}
	public static Service getInstance(String name, String someString){
		Provider p = providers.get(name);
		if(p==null){
			throw new IllegalArgumentException(
					"No provider registererd with name: " + name);
		}
		return p.getService(someString);
	}
}
