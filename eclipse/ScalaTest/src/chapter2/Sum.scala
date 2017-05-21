package chapter2
import scala.io._

object Sum extends App {
  
  def toInt(in: String): Option[Int] = {
    try {
      Some(Integer.parseInt(in.trim))
    } catch {
      case e: NumberFormatException => 
        println(in.trim + " is not Number")
        None
    }
  }
  
  def sum(in: Seq[String]) = {
    val ints = in.flatMap(s => toInt(s))
    ints.foldLeft(0)((a, b) => a + b)
  }
  
  val message = 
"""Enter some numbers..
and press ctrl-D"""
//  println("Enter some numbers .. and press ctrl-D")
  println(message)
  val input = Source.fromInputStream(System.in)
  val lines = input.getLines().toSeq
  println("Sum " + sum(lines))
  
}