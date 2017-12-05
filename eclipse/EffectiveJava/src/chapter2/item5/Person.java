package chapter2.item5;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Riki
 * 項目５
 * 不必要なオブジェクトの生成を避ける
 * staticファクトリーメソッドが用意されている場合、そちらを使用するべき
 */
public class Person {

	/** 
	 * 例：String
	 * やってはいけない。呼び出されるたびにStringインスタンスを生成する
	 */
	String tmpWrong = new String("tmp");
	
	/**
	 * 例：String
	 * こちらを使用するべき
	 * 何度呼び出されても
	 * 同じリテラスの文字列が同一VM上にある場合、使い回す
	 * 
	 */
	String tmpCorrect = "tmp";

	/** 生年月日 */
	private final Date birthDate;

	/**
	 * コンストラクタ
	 * @param birthDate 生年月日
	 */
	public Person(Date birthDate){
		this.birthDate = birthDate;
	}
	
	/**
	 * ベビーブームか否かを判定します。
	 * これをやってはいけない
	 * このメソッドが呼ばれるたびに不要なCalendar、TimeZone、Dateオブジェクトが生成される
	 * @return ベビーブーム生まれ（1946年〜1964年生まれ）
	 */
	public boolean isBabyBoomerWrong(){
		// 追えなかったけど、getInstanceの中でCalendarインスタンスをnewしていると本には書かれている
		// 追えなかったけど、getTimeZoneもTimeZoneインスタンスをnewしていると本には書かれている
		Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);
		// getTimeでDateオブジェクトをnewしている
		Date boomStart = gmtCal.getTime();
		gmtCal.set(1964, Calendar.JANUARY, 1, 0, 0, 0);
		Date boomEnd = gmtCal.getTime();
		return birthDate.compareTo(boomStart) >= 0 && // 大きければ１、等しければ０、小さければ−１をreturnする
			   birthDate.compareTo(boomEnd)   <  0;
	}
	
	/** ベビーブームが開始された年 */
	private static final Date BOOM_START;
	/** ベビーブームが終了した年 */
	private static final Date BOOM_END;

	/**
	 * ベビーブームか否かを判定するロジックに使用するインスタンスを
	 * staticイニシャライザで初期化します。
	 */
	static {
		Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);
		BOOM_START = gmtCal.getTime();
		gmtCal.set(1964, Calendar.JANUARY, 1, 0, 0, 0);
		BOOM_END = gmtCal.getTime();
	}
	
	/**
	 * ベビーブームか否かを判定します。
	 * 判定処理のみを記述しており、こちらが望ましい。
	 * @param birthDate 生年月日
	 * @return 
	 */
	public boolean isBabyBoomCorrect(){
		return birthDate.compareTo(BOOM_START) >= 0 && // 大きければ１、等しければ０、小さければ−１をreturnする
				   birthDate.compareTo(BOOM_END)   <  0;
	}
}
