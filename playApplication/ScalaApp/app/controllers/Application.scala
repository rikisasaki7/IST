package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models._
import anorm._

object Application extends Controller {

  val form1 = Form(
        mapping(
            "id" -> ignored(NotAssigned:Pk[Long]),
            "name" -> text,
            "age" -> number,
            "address" -> text,
            "tel" -> text
        )(AddressBook.apply)(AddressBook.unapply)
  )
  
  def index = Action {
    val book: List[AddressBook] = AddressBook.all
    Ok(views.html.index("住所一覧", book))
  }
  
  def add = Action { implicit request =>
    val reform = form1.bindFromRequest()
    val res = "住所録の登録"
    Ok(views.html.add(res, reform))
  }
  
  def send = Action {implicit request =>
    var reform = form1.bindFromRequest
    reform.get.add
    Redirect("/")
  }
  
}