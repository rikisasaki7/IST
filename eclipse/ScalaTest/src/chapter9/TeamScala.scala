package chapter9

import chapter3.Person.Person

object TeamScala extends App {
  
  // イミュータブル
  def read1(in: java.io.BufferedReader): List[String] = {
    var ret: List[String] = Nil
    var line = in.readLine()
    while(line != null) {
      ret ::= line
      line = in.readLine()
    }
    ret.reverse
  }
  
  // 再帰を使用
  def read2(in: java.io.BufferedReader): List[String] = {
    def doRead(acc: List[String]): List[String] = in.readLine match {
      case null => acc
      case s => doRead(s :: acc)
    }
    doRead(Nil).reverse
  }
  
  // メソッドを短く
  private def readLines(in: java.io.BufferedReader, acc: List[String]): List[String] =
    in.readLine match {
      case null => acc
      case s => readLines(in, s::acc)
    }
    def read3(in: java.io.BufferedReader): List[String] =
      readLines(in, Nil).reverse
      
  // リファクタリング（手続き型のコーディグ）
//  def validByAge(in: List[Person]): List[String] = {
//    var valid: List[Person] = Nil
//    for(p <- in) {
//      if(p.valid) valid = p::valid
//    }
//    def localSortFunction(a: Person, b:Person) = a.age < b.age
//    val people = valid.sortWith(localSortFunction _)
//    var ret: List[String] = Nil
//    for(p <- people){
//      ret = ret ::: List(p.first)
//    }
//    return ret
//  }
  
  // リファクタリング
  def validByAge(in: List[Person]): List[String] = {
    val valid = for(p <- in if p.valid) yield p 
    def localSortFunction(a: Person, b: Person) = a.age < b.age
    val people = valid.sortWith(localSortFunction _)
    for(p <- people) yield p.first
  }
}