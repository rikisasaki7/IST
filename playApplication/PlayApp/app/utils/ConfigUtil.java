package utils;

import java.util.List;

import play.Play;
import play.libs.F.Option;

public class ConfigUtil {

	public static Option<String> get(String key) {
		return OptionUtil.apply(Play.application().configuration().getString(key));
	}
	
	public static Option<List<String>> getByList(String key) {
		return OptionUtil.apply(Play.application().configuration().getStringList(key));
	}	
}
