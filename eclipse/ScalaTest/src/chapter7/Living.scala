package chapter7

// トレイト
object Living extends App {

  println("-----トレイトを使用した型チェック-----")
  abstract class LivingThings
  abstract class Plant extends LivingThings
  abstract class Fungus extends LivingThings
  abstract class Animal extends LivingThings
  
  trait HasLegs extends Animal {
    def walk() = println("Walking")
  }
  trait HasWings extends Animal {
    def flap = println("Flal")
  }
  trait Flies {
    this: HasWings =>
    def fly() = println("I'm flying")
  }
  
  abstract class Bird extends Animal with HasWings with HasLegs
  class Robin extends Bird with Flies
  class Ostrich extends Bird
  abstract class Mammal extends Animal {
    def bodyTemperature: Double
  }
  
  trait KnowsName extends Animal {
    def name: String
  }
  trait IgnoreName {
    this: KnowsName =>
    def ignoreName(when: String): Boolean
    def currentName(when: String): Option[String] =
      if(ignoreName(when)) None else Some(name)
  }
  
  class Dog(val name: String) extends Mammal with HasLegs with KnowsName {
    def bodyTemperature: Double = 99.3
  }
  class Cat(val name: String) extends Mammal with HasLegs with KnowsName with IgnoreName {
    def ignoreName(when: String) = when match {
      case "Dinner" => false
      case _ => true
    }
    def bodyTemperature: Double = 99.5
    
  }
  
  trait Athlete extends Animal
  trait Runner {
    this: Athlete with HasLegs =>
    def run = println("I'm running")
  }
  
  class Person(val name: String) extends Mammal with HasLegs with KnowsName {
    def bodyTemperature: Double = 36.0
  }
  trait Biker extends Person {
    this: Athlete =>
    def rite = println("I'm riding my bike")
  }
  
  trait Gender
  trait Male extends Gender
  trait Female extends Gender
  
  
  // 実行結果の確認
//  val bikerDog = new Dog("biker") with Athlete with Biker　// コメントアウトを外すとエラーになる。
  val archer = new Dog("archer") with Athlete with Runner with Male
  println("archer: " + archer)
  
  val dpp = new Person("David") with Athlete with Biker with Male
  println("dpp: " + dpp)
  
  val john = new Person("John") with Athlete with Runner with Male
  println("john: " + john)
  
  val annette = new Person("Annette") with Athlete with Runner with Female
  println("annette: " + annette)
  
  def goBiking(b: Biker) = println(b.name + " is biking")
  goBiking(dpp)
//  goBiking(annette) // コメントアウトを外すとエラーになる。

  def charityRun(r: Person with Runner) = r.run
  charityRun(annette)
//  charityRun(archer)  // コメントアウトを外すとエラーになる。
  
  def womensRun(r: Runner with Female) = r.run
  womensRun(annette)
  
  val madeline = new Cat("Madeline") with Athlete with Runner with Female
  womensRun(madeline)
  
}