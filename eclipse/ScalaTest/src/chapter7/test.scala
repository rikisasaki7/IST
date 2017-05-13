package chapter7

import java.util.Date

// 暗黙の型変換
object test extends App {
  
  println("-----暗黙の型変換-----")
  def millisToDays(in: Long): Int = (in / (1000L * 3600L * 24L)).toInt
  println(millisToDays(5949440999L))
  
  // 型変換関数→関数名はどうでも良い
  implicit def aaa(d: Date) = { 
    println("暗黙の型変換: aaa")
    d.getTime
  }
  println(millisToDays(new Date))  
}



