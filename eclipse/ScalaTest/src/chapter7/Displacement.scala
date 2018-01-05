package chapter7

// 変位　ー不変、共変、反変ー
object Displacement extends App {

  println("---変位 不変-----")
  println("aaaa")

  // 型引数　不変
  class Holder[T](var data: T)
  def add(in: Holder[Int]) { in.data = in.data + 1 }
  val h = new Holder(0)
  add(h)
  println(h.data.getClass)
  println(h.data)

  val nh = new Holder[Number](33.3d)
  def round(in: Holder[Number]) { in.data = in.data.intValue }
  round(nh)
  println(nh.data.getClass)
  println(nh.data)

  val dh = new Holder(33.3d)
//  round(dh) // コメントアウトを外すとエラーになる。

  println()
  println("---変位 共変-----")

  class Getable[+T](val data: T)
  def get(in: Getable[Any]) { println("It's " + in.data) }

  val gs = new Getable("String")
  get(gs)

  def getNum(in: Getable[Number]) = in.data.intValue
  def gd = new Getable(new java.lang.Double(33.3))
  println(getNum(gd))

  println()
  println("---変位 反変-----")

  class Putable[-T] {
    def put(in: T) { println("Putting " + in) }
  }
  def writeOnly(in: Putable[String]) { in.put("Hellow") }

  val p = new Putable[AnyRef]
  writeOnly(p)

  println()
  println("---変位 共変と反変-----")

  trait DS[-In, +Out] { def apply(i: In): Out }
  val t1 = new DS[Any, Int] { def apply(i: Any) = i.toString.toInt }
  println("DS: " + t1)

  def check(in: DS[String, Any]) = in("333")
  print("check: ")
  println(check(t1))
  println(check(t1).getClass())
}