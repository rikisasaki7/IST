package chapter5

object Sample2 extends App {
  
  println("＝＝＝＝＝＝＝＝＝＝＝＝＝関数としてのパターンマッチ＝＝＝＝＝＝＝＝＝＝＝＝＝")
  val list = List("1", "2", "3", 4, 5, "6", 7L)
  // パターンマッチをメソッドの引数にできる
  val filtered = list.filter(a => a match { 
    case s: String => true
    case _ => false
  })
  println(filtered)
  // 上の糖衣構文
  val filtered2 = list.filter {
    case s: String => true
    case _ => false
  }
  println(filtered2)
  
  /**
   * 渡されたURLが通常のものかか特殊なものかを判定します。
   * 特殊なURLの場合（引数として渡す部分関数にマッチする場合）、specialCareとして渡された部分関数を実行します。
   * @param req: List[String] 解析対象のURL
   * @param specialCare: PartialFunction[List[String], String] 部分関数
   */
  def handleRequest(req: List[String])(specialCare: PartialFunction[List[String], String]): String =
    if(specialCare.isDefinedAt(req)) {
      println("handleRequest: isDefinedAt => true")
      specialCare(req) 
    } else {
      println("handleRequest: isDefinedAt => false")
      "handling URL " + req + " in the normal way"
    }
  
  def doApi(call: String, params: List[String]): String = 
    "Doing API call " + call + " and params is " + params

    /** 上記handleRequestの呼び出し false */
  val apiResultFoo = handleRequest("foo" :: Nil) {
    case "api" :: call :: params => 
      println("doAPI!!!")
      val result = doApi(call, params)
      println("result: " + result)
      result
  }
  println(apiResultFoo)

  /** 上記handleRequestの呼び出し true */
  val apiResultApi = handleRequest("api" :: "calling!" :: "aa" :: "bb" :: Nil) {
    case "api" :: call :: params => 
      println("doAPI!!!")
      val result = doApi(call, params)
      println("result: " + result)
      result
  }
  println(apiResultApi)
  
  // 複数の部分関数を一つの関数に合成
  val f1: PartialFunction[List[String], String] = {
    case "staff" :: Nil => "Got some stuff"
  }
  val f2: PartialFunction[List[String], String] = {
    case "other" :: params => "other: " + params
  }
  // f1 もしくは f2　match式のcaseをそれぞれf1、f2としてオブジェクトにしているイメージ
  val f3 = f1 orElse f2
  // f1にマッチする
  val compositionResult = handleRequest("staff" :: Nil)(f3)
  println("compositionResult: " + compositionResult)
  // f2にマッチする
  val compositionResult2 = handleRequest("other" :: "staff" :: Nil)(f3)
  println("compositionResult2: " + compositionResult2)
}