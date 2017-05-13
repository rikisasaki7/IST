package models.services.Check;

import org.junit.Test;
import static org.fest.assertions.Assertions.*;

import java.util.List;

import com.avaje.ebean.Ebean;

import apps.FakeApp;
import fixture.CheckFixture;
import models.entity.Check;
import play.libs.F;
import play.libs.F.Option;
import play.libs.F.Some;

public class CheckModelServiceTest extends FakeApp{

	// 使用するserviceを取得
	CheckModelService service = CheckModelService.use();

	// テスト
	/*
	 * 正常系　検索
	 * １件のレコードから１つ取り出す
	 * @throws Exception
	 */
	@Test
	public void testFindByIdTo1ReturnSome() throws Exception {
		Ebean.execute(Ebean.createSqlUpdate(CheckFixture.INSERT_CHECK_ONE_RECORD));
		Option<Check> model = service.findById(1L);
		assertThat(model.getClass()).isEqualTo(Some.class);
		assertThat(model.get().getId()).isEqualTo(1L);
		assertThat(model.get().getName()).isEqualTo("kara_d");
		assertThat(model.get().getResult()).isEqualTo("resultです");
	}
	
	/*
	 * 正常系　検索
	 * １件のレコードから該当しないIdのものを取り出す
	 * @throws Exception
	 */
	@Test
	public void testFindByIdTo2ReturnNone() throws Exception {
		Ebean.execute(Ebean.createSqlUpdate(CheckFixture.INSERT_CHECK_ONE_RECORD));
		Option<Check> model = service.findById(2L);
		assertThat(model.getClass()).isEqualTo(F.None.class);
		assertThat(model.isDefined()).isFalse();
	}
	
	/*
	 * 異常系　検索
	 * １件のレコードから検索のキーとしてnullを渡す
	 * @throws Exception
	 */
	@Test
	public void testFindByIdToNullReturnNone() throws Exception {
		Ebean.execute(Ebean.createSqlUpdate(CheckFixture.INSERT_CHECK_ONE_RECORD));
		Option<Check> model = service.findById(null);
		assertThat(model.getClass()).isEqualTo(F.None.class);
		assertThat(model.isDefined()).isFalse();
	}

	/*
	 * 正常系　保存
	 * Checkモデルのインスタンスを作成し、データベースに保存する
	 * @throws Exception
	 */
	@Test
	public void testSaveToRightParamSuccess() {
		Check entry = new Check("kara_d", "result");
		Option<Check> result = service.save(entry);
		assertThat(result.getClass()).isEqualTo(Some.class);
		assertThat(result.get().getId()).isEqualTo(1L);
	}

	/*
	 * 異常系　保存
	 * Checkモデルのインスタンスがnullだが、データベースに保存する
	 * @throws Exception
	 */
	@Test
	public void testSaveToNullFalse() throws Exception{
		Check entry = null;
		Option<Check> result = service.save(entry);
		assertThat(result.getClass()).isEqualTo(F.None.class);
	}

	/*
	 * 正常系　ページング
	 * ページ１に１０件存在し、IDが１と１０が存在する
	 * @throws Exception
	 */
	@Test
	public void testFindWithPage1Contains1And10() throws Exception {
		Ebean.execute(Ebean.createSqlUpdate(CheckFixture.INSERT_CHECK_FIFTEEN_RECORD));
		Option<List<Check>> result = service.findWithPage(1);
		assertThat(result.getClass()).isEqualTo(Some.class);
		assertThat(result.get().size()).isEqualTo(10);
		assertThat(result.get().get(0).getId()).isEqualTo(1L);
		assertThat(result.get().get(9).getId()).isEqualTo(10L);	
	}

	/*
	 * 正常系　ページング
	 * ページ２に５件存在し、IDが１１と１５が存在する
	 * @throws Exception
	 */
	@Test
	public void testFindWithPage2Contains11And15() throws Exception {
		Ebean.execute(Ebean.createSqlUpdate(CheckFixture.INSERT_CHECK_FIFTEEN_RECORD));
		Option<List<Check>> result = service.findWithPage(2);
		assertThat(result.getClass()).isEqualTo(Some.class);
		assertThat(result.get().size()).isEqualTo(5);
		assertThat(result.get().get(0).getId()).isEqualTo(11L);
		assertThat(result.get().get(4).getId()).isEqualTo(15L);	
	}
	
	/*
	 * 正常系　ページング
	 * ページ３はない
	 * @throws Exception
	 */
	@Test
	public void testFindWithPage3ReturnNone() throws Exception {
		Ebean.execute(Ebean.createSqlUpdate(CheckFixture.INSERT_CHECK_FIFTEEN_RECORD));
		Option<List<Check>> result = service.findWithPage(3);
		assertThat(result.getClass()).isEqualTo(F.None.class);
	}

	/*
	 * 正常系　ページング  
	 * １件しかデータがない場合はMaxPageは１
	 * @throws Exception
	 */
	@Test
	public void testGetMaxPageIs1() throws Exception {
		Ebean.execute(Ebean.createSqlUpdate(CheckFixture.INSERT_CHECK_ONE_RECORD));
		Option<Integer> result = service.getMaxPage();
		assertThat(result.getClass()).isEqualTo(Some.class);
		assertThat(result.get()).isEqualTo(1);
	}

	/*
	 * 正常系　ページング  
	 * ０件しかデータがない場合はMaxPageは０
	 * @throws Exception
	 */
	@Test
	public void testGetMaxpageIsZero() throws Exception {
		Option<Integer> result = service.getMaxPage();
		assertThat(result.getClass()).isEqualTo(Some.class);
		assertThat(result.get()).isEqualTo(0);
	}

	/*
	 * 正常系　ページング  
	 * １５件データがある場合はMaxPageは２
	 * @throws Exception
	 */
	@Test
	public void testGetMaxPageis2() throws Exception {
		Ebean.execute(Ebean.createSqlUpdate(CheckFixture.INSERT_CHECK_FIFTEEN_RECORD));
		Option<Integer> result = service.getMaxPage();
		assertThat(result.getClass()).isEqualTo(Some.class);
		assertThat(result.get()).isEqualTo(2);

		
	}
}
