package models.services.Check;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import play.libs.F.Option;
import utils.ConfigUtil;
import utils.OptionUtil;

public class CheckService {

	// privateコンストラクタ
	private CheckService(){};
	
	// シングルトンserviceオブジェクト
	private static CheckService checkService = new CheckService();
	
	// serviceオブジェクトの使用
	public static CheckService use() {
		return checkService;
	}
	
	@SuppressWarnings("serial")
	public Option<String> getResult(String name) {
		List<String> versions = ConfigUtil.getByList("checkyou.setting.answer")
										  .getOrElse(new ArrayList<String>(){{add("2.1.3 Java");}});
		Collections.shuffle(versions);
		return getResultText(name, versions.get(0));
	}
	
	public Option<String> getResultText(String name, String version) {
		StringBuilder result = new StringBuilder();
		result.append(name);
		result.append(ConfigUtil.get("checkyou.setting.message.result").getOrElse("-"));
		result.append(version);
		result.append(ConfigUtil.get("checkyou.setting.message.resultSuffix").getOrElse("."));
		return OptionUtil.apply(result.toString());
	}

}
