package models
import play.api.db._
import anorm._
import play.api.Play.current
import anorm.SqlParser._
import play.api.db.DB

case class AddressBook(
    id:Pk[Long], 
    name: String, 
    age: Int,
    tel: String,
    address: String) {
  
  def add {
    DB.withConnection { implicit c =>
      val id: Int = SQL("insert into addressbook(name, age, tel, address) values ({name}, {age}, {tel}, {address})").
                    on('name->this.name, 'age->this.age, 'tel->this.tel, 'address->this.address).executeUpdate()
      
    }
  }
}

object AddressBook {
  val data = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[Int]("age") ~
    get[String]("tel") ~
    get[String]("address") map {
      case id ~name ~age ~tel ~address => AddressBook(id, name, age, tel, address)
    }
  }
  
  def all: List[AddressBook] = {
    DB.withConnection { implicit c =>
      val datas = SQL("select * from addressbook").as(AddressBook.data *)
      return datas
    }
  }
}