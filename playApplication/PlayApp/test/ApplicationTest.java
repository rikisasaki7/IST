import org.junit.*;

import controllers.Application.SampleForm;
import models.Message;
import play.mvc.*;

import static play.test.Helpers.*;

import java.util.ArrayList;

import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

//    @Test 
//    public void simpleCheck() {
//        int a = 1 + 1;
//        assertThat(a).isEqualTo(2);
//    }
//    
	@Ignore
	@Test
    public void renderTemplate() {
    	String pageTitle = "タイトル";
//        Content html = views.html.index.render(pageTitle,
//        		new play.data.Form<SampleForm>(
//        				controllers.Application.SampleForm.class));
        Content html = views.html.index.render(pageTitle, new ArrayList<Message>());
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains(pageTitle);
    }
  
   
}
