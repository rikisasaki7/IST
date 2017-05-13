package utils;

import org.junit.Test;
import static org.fest.assertions.Assertions.assertThat;

import java.util.*;

import play.libs.F;

public class OptionUtilTest {

	@Test
	public void testApplyShouldSome() throws Exception {
		String test1 = "abc";
		F.Option<String> test1Result = OptionUtil.apply(test1);
		assertThat(test1Result.getClass()).isEqualTo(F.Some.class);
		assertThat(test1Result.getClass()).isNotEqualTo(F.None.class);		
	}
	
	@Test
	public void testApplyShouldNone() throws Exception {
		String test2 = null;
		F.Option<String> test2Result = OptionUtil.apply(test2);
		assertThat(test2Result.getClass()).isEqualTo(F.None.class);
		assertThat(test2Result.getClass()).isNotEqualTo(F.Some.class);		
	}
	
	@Test
	public void testApplyShouldSomeList() throws Exception {
		@SuppressWarnings("serial")
		List<String> test3 = new ArrayList<String>() {
			{
				add("a"); 
				add("b"); 
				add("c");
			}
		};
		F.Option<List<String>> test3Result = OptionUtil.apply(test3);
		assertThat(test3Result.getClass()).isEqualTo(F.Some.class);
		assertThat(test3Result.getClass()).isNotEqualTo(F.None.class);		
	}
	
	@Test
	public void testApplyShouldNoneList() throws Exception {
		List<String> test4 = new ArrayList<String>();
		F.Option<List<String>> test4Result = OptionUtil.apply(test4);
		assertThat(test4Result.getClass()).isEqualTo(F.None.class);
		assertThat(test4Result.getClass()).isNotEqualTo(F.Some.class);				
	}
	
	@Test
	public void testAsScalaShouldSome() throws Exception {
		String s = "String";
		F.Option<String> result = OptionUtil.apply(s);
		scala.Option<String> resultScala = OptionUtil.asScala(result);
		assertThat(result.getClass()).isEqualTo(F.Some.class);
		assertThat(resultScala.getClass()).isEqualTo(scala.Some.class);		
	}

	@Test
	public void testAsScalaShouldNone() throws Exception {
		String n = null;
		F.Option<String> result = OptionUtil.apply(n);
		scala.Option<String> resultScala = OptionUtil.asScala(result);
		assertThat(result.getClass()).isEqualTo(F.None.class);
		assertThat(resultScala.getClass()).isEqualTo(scala.None$.class);		
	}

}
