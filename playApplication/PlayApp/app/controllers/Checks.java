package controllers;

import models.entity.Check;
import models.request.Check.ResultPostRequest;
import play.data.Form;
import play.libs.F.Option;

import static play.data.Form.form;

import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import utils.ConfigUtil;

public class Checks extends Apps {

	public static Result index() {
		Form<ResultPostRequest> form = form(ResultPostRequest.class);
		return ok(views.html.check.index.render(ConfigUtil.get("checkyou.setting.message.title").getOrElse("")
				, ConfigUtil.get("checkyou.setting.message.question").getOrElse("")
				, form));
	}
	
	public static Result result() {
		Form<ResultPostRequest> form = form(ResultPostRequest.class).bindFromRequest();
		//error.required
		if(form.error("name") != null && form.error("name").message().contains("error.required")) {
			return validationErrorIndexResult("名前欄が空です。", form);
		}
		
		//error.pattern
		if(form.error("name") != null && form.error("name").message().contains("error.pattern")) {
			return validationErrorIndexResult("Twitter ID形式ではありません。", form);
		}

		//error.maxLength
		if(form.error("name") != null && form.error("name").message().contains("error.maxLength")) {
			return validationErrorIndexResult("文字数が１５文字を超えています。", form);
		}
		
		String name = form.data().get("name");
		Check checkOrigin = new Check(name, new Check(name).result().getOrElse(ConfigUtil.get("checkyou.setting.message.failCheck").getOrElse("")));
		Option<Check> checkOps = checkOrigin.store();
		return checkOps.isDefined() 
				? ok(views.html.check.result.render(
						checkOps.get().getName() + 
							ConfigUtil.get("checkyou.setting.message.resultTitle").getOrElse("")
						, checkOps.get()))
				: Apps.fail(routes.Checks.index(), "error", "診断エラーです。");
	}
	
	public static Result resultId(Long id) {
		Option<Check> check = new Check(id).unique();
		return check.isDefined()
				? ok(views.html.check.result.render(
						check.get().getName()
						+ ConfigUtil.get("checkyou.setting.message.resultTitle").getOrElse("")
						, check.get()))
				: Apps.fail(routes.Checks.index(), "error", "診断エラーです");
	}
	
	public static Result recent(Integer page) {
		Option<List<Check>> result = new Check().entitiesList(page);
		Integer maxPage = new Check().entitiesMaxPage(page);
		return result.isDefined()
				? ok(views.html.check.recent.render(
						ConfigUtil.get("checkyou.setting.message.recentTitle").getOrElse("")
						, ConfigUtil.get("checkyou.setting.message.recentDescription").getOrElse("")
						, result.get()
						, page
						, maxPage))
				: ok(views.html.check.recentEmpty.render(
						ConfigUtil.get("checkyou.setting.message.recentTitle").getOrElse("")
						, ConfigUtil.get("checkyou.setting.message.recentDescriptionNone").getOrElse("")));
	}
	
	/*
	 * バリデーションエラーメッセージを表示し、トップページへ戻る
	 * @param message
	 * @return
	 */
	private static Result validationErrorIndexResult(String message, Form<ResultPostRequest> form) {
		flash("error", message);
		return badRequest(views.html.check.index.render(
				ConfigUtil.get("checkyou.setting.message.title").getOrElse("")
				, ConfigUtil.get("checkyou.setting.message.question").getOrElse("")
				, form));
	}
}
