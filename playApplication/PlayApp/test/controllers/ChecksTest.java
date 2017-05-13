package controllers;

import org.junit.Test;

import com.avaje.ebean.Ebean;

import apps.FakeApp;
import fixture.CheckFixture;
import play.mvc.Result;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.assertThat;

public class ChecksTest extends FakeApp {

	/*
	 * 通常の/へのアクセス
	 * @throws Exception
	 */
	@Test
	public void testIndexIsOk() throws Exception {
		Result result = route(fakeRequest(GET, "/"));
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("text/html");
		assertThat(charset(result)).isEqualTo("utf-8");
		assertThat(contentAsString(result)).contains("Twitterのユーザー名を入れてください");
	}
	
	/*
	 * IDつきの/resultへのアクセス
	 * @throws Exception
	 */
	@Test
	public void testResultIdWithId() throws Exception {
		Ebean.execute(Ebean.createSqlUpdate(CheckFixture.INSERT_CHECK_ONE_RECORD));
		Result result = route(fakeRequest(GET, "/result/1"));
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("text/html");
		assertThat(charset(result)).isEqualTo("utf-8");
		assertThat(contentAsString(result)).contains("kara_d");
	}

	/*
	 * １ページ目にはkara_1とkara_10が含まれる
	 * @throws Exception
	 */
	@Test
	public void testRecentShouldContain1And10() throws Exception {
		fixture.CheckFixture.createPaginateRecords();
		Result result = route(fakeRequest(GET, "/recent/1"));
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("text/html");
		assertThat(charset(result)).isEqualTo("utf-8");
		assertThat(contentAsString(result)).contains("kara_1");
		assertThat(contentAsString(result)).contains("kara_10");
	}

	/*
	 * １ページ目にはkara_11は含まれない
	 * @throws Exception
	 */
	@Test
	public void testRecentShouldNotContain11() throws Exception {
		fixture.CheckFixture.createPaginateRecords();
		Result result = route(fakeRequest(GET, "/recent/1"));
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("text/html");
		assertThat(charset(result)).isEqualTo("utf-8");
		assertThat(contentAsString(result)).doesNotContain("kara_11");
	}

	/*
	 * ２ページ目にはkara_11jとkara_15を含む
	 * @throws Exception
	 */
	@Test
	public void testRecentPage2ShouldContain11And15() throws Exception {
		fixture.CheckFixture.createPaginateRecords();
		Result result = route(fakeRequest(GET, "/recent/2"));
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("text/html");
		assertThat(charset(result)).isEqualTo("utf-8");
		assertThat(contentAsString(result)).contains("kara_11");
		assertThat(contentAsString(result)).contains("kara_15");
	}
}
