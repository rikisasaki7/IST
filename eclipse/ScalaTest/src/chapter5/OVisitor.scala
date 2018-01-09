package chapter5

trait OCarVisitor {
  def visit(wheel: OWheel) = System.out.println("owheel and name: " + wheel.name)
  def visit(engine: OEngine) = System.out.println("oengine")
  def visit(body: OBody) = System.out.println("obody")
  def visit(car: OCar) = System.out.println("ocar")
}
object OCarVisitor extends OCarVisitor

trait OCarElement {
  def accept(visitor: OCarVisitor): Unit
}

class OWheel(val name: String) extends OCarElement {
  def accept(visitor: OCarVisitor) = visitor.visit(this)  
}
class OEngine extends OCarElement {
  def accept(visitor: OCarVisitor) = visitor.visit(this)
}
class OBody extends OCarElement {
  def accept(visitor: OCarVisitor) = visitor.visit(this)
}
class OCar extends OCarElement {
  val elements = List(new OEngine, new OBody, new OWheel("OFR"), new OWheel("OFL"), new OWheel("ORR"), new OWheel("ORL"))
  def accept(visitor: OCarVisitor) = elements.foreach(_.accept(visitor))
}

trait CarElement{
  def doSomething(in: CarElement): Unit = in match {
    case Wheel(name) => System.out.println(s"wheel and name: ${name}")
    case Engine() => System.out.println("engine")
    case Body() => System.out.println("body")
    case Car(e) => e.foreach(doSomething)
  }  
}
object CarElement extends CarElement
case class Wheel(name: String) extends CarElement
case class Engine() extends CarElement
case class Body() extends CarElement
case class Car(element: List[CarElement]) extends CarElement

object visitor extends App {
  // オブジェクト思考のビジターパターン
  new OWheel("aaa").accept(OCarVisitor)
  new OCar().accept(OCarVisitor)
  
  // パターンマッチのビジターパターン代替
  val elements = List(new Engine, new Body, new Wheel("FR"), new Wheel("FL"), new Wheel("RR"), new Wheel("RL"))
  CarElement.doSomething(Car(elements))
}