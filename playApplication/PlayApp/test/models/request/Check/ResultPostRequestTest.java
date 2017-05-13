package models.request.Check;

import java.util.HashMap;
import java.util.Map;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import play.data.Form;
import static play.data.Form.form;

public class ResultPostRequestTest {

	/*
	 * 正しいID形式
	 */
	@Test
	public void testValidationToRightParam() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "karad");
		Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
		assertThat(form.hasErrors()).isFalse();
	}

	/*
	 * 禁止文字を含むID形式
	 */
	@Test
	public void testValidationToWrongParam() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "kara_>d");
		Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
		assertThat(form.hasErrors()).isTrue();
	}
}
