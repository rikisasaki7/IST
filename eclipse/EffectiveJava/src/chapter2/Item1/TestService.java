/**
 * 
 */
package chapter2.Item1;

/**
 * @author Riki
 * テスト用に作成したService
 *
 */
public class TestService implements Service {
	
	/** どの呼び出し時にも使用する強調したい文字列 */
	private String powerString = " serviced by POWER!!! ";
	/** 好きな文字  */
	private String someString = "";

	// コンストラクタ
	public TestService(){}
	public TestService(String s){
		setSomeString(s);
	}
	@Override
	public String writeString(String s){
		return "get: " + s + someString + powerString;
	}
	
	/**
	 * @return 好きな文字
	 */
	public String getSomeString() {
		return someString;
	}

	/**
	 * @param someString 好きな文字
	 */
	public void setSomeString(String someString) {
		this.someString = someString;
	}
}
