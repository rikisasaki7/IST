package chapter7

// トレイト
object Listener extends App {

  println("-----トレイトを使用したリスナー-----")
  case class ChangeEvent[OnType](on: OnType)
  trait Listener[T] {
    this: T with Listener[T] =>
    type ChangeHandler = {def changed(c: ChangeEvent[T]): Unit }
    private var listeners: List[ChangeHandler] = Nil
  
    def addListener(c: ChangeHandler) = synchronized { 
      val list = c :: listeners 
      println("addedList: " + list)
      listeners = c :: listeners 
    }
    def removeListener(c: ChangeHandler) = synchronized { 
      val list = listeners.filter(_ != c) 
      println("removedList: " + list)
      listeners.filter(_ != c) 
    }
  
    protected def updateListeners() = synchronized {
      val ch = ChangeEvent[T](this)
      listeners.foreach(i => i.changed(ch))
    }
  }
  
  class Foo extends Listener[Foo] {
    private var _count = 0
    def count = synchronized{ _count }
    def inc = synchronized {
      _count += 1
      updateListeners()
    }
  }

  // 実行結果の確認
  val f = new Foo
  object Bar {
    def changed(c: ChangeEvent[Foo]) { println("changed: "+ c.on.count) }
  }
  f.addListener(Bar)
  f.inc
  f.inc
  f.removeListener(Bar)
  f.inc
}

