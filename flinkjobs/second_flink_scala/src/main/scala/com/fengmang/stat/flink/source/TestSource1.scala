package com.fengmang.stat.flink.source

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/14 10:44
 *
 */
object TestSource1 {

  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    import org.apache.flink.api.scala._

    //从collection 中读取数据
//    val value: DataStream[String] = env.fromCollection(List("a", "b", "c", "d"))

    val value: DataStream[String] = env.fromElements("a", "b", "c", "d")

//    value.setParallelism(1).print()   //设置并行度

//    value.print()
    value.print("prefix")

    try {
      env.execute()
    } catch {
      case exception: Exception => println(exception)
    }

  }

}
