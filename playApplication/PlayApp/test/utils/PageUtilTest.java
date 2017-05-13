package utils;

import org.junit.Test;
import static org.fest.assertions.Assertions.assertThat;

public class PageUtilTest {

	/**
	 * ページ数が０のとき
	 * @throws Exception
	 */
	@Test
	public void testRightPageTo0Should0() throws Exception{
		assertThat(PageUtil.rightPage(0)).isEqualTo(0);
	}
	/**
	 * ページ数がー１のとき
	 * @throws Exception
	 */
	@Test
	public void testRightPageToMinus1Should0() throws Exception{
		assertThat(PageUtil.rightPage(-1)).isEqualTo(0);
	}
	/**
	 * ページ数が１のとき
	 * @throws Exception
	 */
	@Test
	public void testRightPageTo1Should0() throws Exception{
		assertThat(PageUtil.rightPage(1)).isEqualTo(0);
	}
	/**
	 * ページ数が２のとき
	 * @throws Exception
	 */
	@Test
	public void testRightPageTo2Should1() throws Exception{
		assertThat(PageUtil.rightPage(2)).isEqualTo(1);
	}
	/**
	 * ページ数が１０のとき
	 * @throws Exception
	 */
	@Test
	public void testRightPageTo10Should9() throws Exception{
		assertThat(PageUtil.rightPage(10)).isEqualTo(9);
	}
	/**
	 * ページ数がnullのとき
	 * @throws Exception
	 */
	@Test
	public void testRightPageToNullShould0() throws Exception{
		assertThat(PageUtil.rightPage(null)).isEqualTo(0);
	}
}
