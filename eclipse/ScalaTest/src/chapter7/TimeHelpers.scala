package chapter7

import java.util.Date

// ライブラリの大改造 期間DSLの作成
object TimeHelpers extends App {
  println("-----ライブラリの大改造 期間DSLの作成-----")
  case class TimeSpanBuilder(val len: Long) {
    def seconds = TimeSpan(TimeHelpers.seconds(len))
    def second = seconds
    def minutes = TimeSpan(TimeHelpers.minutes(len))
    def minute = minutes
    def hours = TimeSpan(TimeHelpers.hours(len))
    def hour = hours
    def days = TimeSpan(TimeHelpers.days(len))
    def day = days
    def weeks = TimeSpan(TimeHelpers.weeks(len))
    def week = weeks
  }
  
  def seconds(in: Long) = in * 1000L
  def minutes(in: Long) = seconds(in) * 60L
  def hours(in: Long) = minutes(in) * 60L
  def days(in: Long) = hours(in) * 24L
  def weeks(in: Long) = days(in) * 7L
  
  implicit def longToTimeSpanBuilder(in: Long): TimeSpanBuilder = TimeSpanBuilder(in)
  implicit def intToTimeSpanBuilder(in: Int): TimeSpanBuilder = {
    println("intToTimeSpanBuilder!!")
    TimeSpanBuilder(in)
  }
  def millis = System.currentTimeMillis
  
  case class TimeSpan(millis: Long) extends Ordered[TimeSpan] {
    def later = new Date(millis + TimeHelpers.millis)
    def ago = new Date(TimeHelpers.millis - millis)
    def +(in: TimeSpan) = TimeSpan(this.millis + in.millis)
    def -(in: TimeSpan) = TimeSpan(this.millis - in.millis)
    
    def compare(other: TimeSpan) = millis compare other.millis
  }
  
  object TimeSpan {
    implicit def tsToMillis(in: TimeSpan): Long = {
      println("tsToMillis")
      in.millis
    }
  }
  
  class DateMath(d: Date) {
    def +(ts: TimeSpan) = new Date(d.getTime + ts.millis)
    def -(ts: TimeSpan) = new Date(d.getTime - ts.millis)
  }
  
  implicit def dateToDm(d: Date) = {
    println("dateToDm")
    new DateMath(d)
  }

  // 実行結果の確認
  // 暗黙の型変換が動作しimplicitの関数が実行される
  println(1.days)
  println(5.days + 2.hours)
  println((5.days + 2.hours).later)

  val d = new Date("January 2, 2005")
  println(d + 8.weeks)

  val lng: Long = 7.days + 2.hours + 4.minutes
  println(lng)
  
}