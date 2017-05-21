package chapter2
import Moose._
import java.io.BufferedReader

object MyFile extends App {
  println("Hello World")
  for (i <- 1 to 10) println(i)
  for { i <- 1 to 10
        j <- 1 to 10 } {
        print("i:" + i + " j:" + j)
        print(" i*j:" + i*j + " ")
      }
   println

   // クラス、トレイト、オブジェクトの定義
   new Foo
   new Bar("Working...")
   
   // メソッド定義
   def myMethod(): String = "Moof"
   def myOtherMethod() = "Moof"
   def foo(a: Int): String = a.toString()
   def f2(a: Int, b: Boolean): String = if(b) a.toString() else "false"
   def list[T](p: T): List[T] = p::Nil
   list(1)
   list("Hello")
   
   def largest(as: Int*): Int = as.reduceLeft((a,b) => a max b)
   largest(1)
   largest(2, 3, 99)
   largest(33, 22, 33, 22)
   
   def mkString [T](as: T*): String = as.foldLeft("")(_ + _.toString())
   def sum [T <: Number](as: T*): Double = as.foldLeft(0d)(_ + _.doubleValue())
   def readLines(br: BufferedReader) = {
     var ret: List[String] = Nil
     
     def readAll(): Unit = br.readLine() match {
       case null =>
       case s => ret ::= s; readAll()
     }
     
     readAll
     ret.reverse
   }
   
   // 変数の定義
   var y: String = "Moof"
   val x: String = "Moof"
   lazy val lz: String = myOtherMethod()
   val (i1: Int, s1: String) = Pair(33, "Moof")
   val (i2, s2) = Pair(43, "Woof")
   
   // コードブロック
   def meth3(): String = { "Moof" }
   def meth4(): String = {
     val d = new java.util.Date()
     d.toString()
   }
   val x3: String = {
     val d = new java.util.Date()
     d.toString()
   }
   
   // 名前渡し
   def nano() = {
     println("Getting nano")
     System.nanoTime()
   }
   // 名前渡し：tにアクセスするたび、コードブロック（nano）を実行する
   // delayedメソッドの中で２回tにアクセスしているため、
   // コードブロック（nano）は２回実行される
   def delayed(t: => Long) = {
     println("In delayed method")
     println("Param: " + t)
     t
   }
   // 参照渡し：notDelayedメソッドが実行される前にコードブロック（nano）
   // が実行される
   def notDelayed(t: Long) = {
     println("In not delayed method")
     println("Param: " + t)
     t
   }
   delayed(nano())
   notDelayed(nano())
   
   // メソッド呼び出し
   // .でのアクセスは「 （スペース）」でも代替可能
   // ※　ただし、メソッド実行の場合は引数が一つの場合のみ
   MyFile foo 3
   // ↓は２つともコンパイルエラー
//   MyFile f2 3 false
//   MyFile largest 3 50

   // 関数、apply、updateとコンパイラのマジック
   def answer(f: Function1[Int, String]) = f.apply(42)
   // applyメソッドが実装されているインスタンスの場合、
   // applyの明示的な呼び出しも不要　↓を参照
   def answer2(f: Function1[Int, String]) = f(42)
   // シンタックスシュガー（糖衣構文）を使用した呼び出し
   def answer3(f: Int => String) = f(42)
   // これはFunction2トレイトを使用している
   // IntとStringを引数に、Stringをreturnする
   def answer4(f: (Int, String) => String) = f(3, "String")
   val ap = new Ap
   ap(42)
   
}

class Foo
class Bar(name: String){
  
  // 初期化処理
  if(name == null) throw new Exception("Name is null")
  else println("Bar: "+ name)
  
  // コンストラクタのオーバーロード（複数コンストラクタの定義）
  // http://qiita.com/takudo/items/8731612acbe7eae83183
  def this(name: String, age: Int) = {
    this(name)
    println("Bar's age is " + age)
  }
}

trait Dog
class Fizz2(name: String) extends Bar(name: String) with Dog

trait Cat {
  def meow(): String
}

trait FuzzyCat extends Cat {
  override def meow(): String = "Meeow"
}

trait OtherThing {
  def hello() = 4
}

// extendsとwithの違い
// withはextendsが指定済かつ複数のtraitをmixinしたいときに指定する
// http://qiita.com/f81@github/items/5b96af593812286eec49
class Yep extends FuzzyCat with OtherThing

object Simple
object OneMethod {
  def myMethod = "Only one"
}

object Dude extends Yep
object Dude2 extends Yep {
  override def meow() = "Dude looks like a cat"
}

object OtherDude extends Yep {
  def twoMeows(param: Yep) = meow + ", " + param.meow()
}

class HasYep {
  object myYep extends Yep {
    override def meow = "Moof"
  }
}

class HasClass {
  class MyDude extends FuzzyCat
  def makeOne(): FuzzyCat = new MyDude
}

object Moose {
  def bark = "woof"
}

abstract class Base {
  def thing: String
}
class One extends Base {
  def thing = "Moof"
}
class Two extends One {
  override val thing = (new java.util.Date).toString()
}
class Three extends One {
  override lazy val thing = super.thing + (new java.util.Date).toString()
}

// 関数、apply、updateとコンパイラのマジック
class Ap {
  def apply(in: Int) = {
    println("Called Ap.apply!")
    println("Param:" + in)
    in.toString()
  }
}