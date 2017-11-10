/**
 * 
 */
package chapter2.item1;

/**
 * @author Riki
 * テスト用に作成したProvider
 */
public class TestProvider implements Provider {

	/** デフォルトで使用するインスタンス */
	private TestService defaultTestService = new TestService();
	
	/** 
	 * @return TestProviderが提供するService
	 * Serviceインタフェースを継承したTestServiceをreturn
	 */
	@Override
	public Service newService(){
		return new TestService();
	}
	
	/**
	 * @param s サービスに設定できる好きな文字
	 * @return デフォルトのサービス
	 */
	@Override
	public Service getService(String s){
		if(!s.isEmpty()) defaultTestService.setSomeString(s);
		return defaultTestService;
	}
}
