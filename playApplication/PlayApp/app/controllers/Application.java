package controllers;

import play.data.*;
import static play.data.Form.*;

import models.Message;
import play.mvc.*;
import java.util.*;
import models.*;

import views.html.*;

public class Application extends Controller {
  
	// From用の内部クラス
	public static class SampleForm {
		public String message;
	}

	// ルートにアクセスした際のAction
	public static Result index() {
		List<Message> datas = Message.find.all();
		return ok(index.render("データベースのサンプル", datas));
	}

	// 新規作成フォームのAction
	public static Result add() {
		Form<Message> f = new Form<Message>(Message.class);
		return ok(add.render("投稿フォーム", f));
	}
	
	// createにアクセスした時のAction
	public static Result create() {
		Form<Message> f = new Form<Message>(Message.class).bindFromRequest();
		if(!f.hasErrors()){
			Message data = f.get();
			data.save();
			return redirect("/");
		} else {
			return badRequest(add.render("ERROR", f));
		}
	}
	
//	public static Result index() {
//        return ok(index.render("何か書いて", new Form<SampleForm>(SampleForm.class)));
//    }

	// /sendにアクセスした際のAction
//	public static Result send() {
//		Form<SampleForm> f = form(SampleForm.class).bindFromRequest();
//		if(!f.hasErrors()){
//			SampleForm data = f.get();
//			String msg = "you typed" + data.message;
//			return ok(index.render(msg, f));
//		} else {
//			return badRequest(index.render("ERROR", form(SampleForm.class)));
//		}
//	}
//	
}
