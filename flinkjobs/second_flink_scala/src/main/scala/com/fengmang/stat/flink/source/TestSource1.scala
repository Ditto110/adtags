package com.fengmang.stat.flink.source

import org.apache.flink.streaming.api.functions.ProcessFunction
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
//    value.process(ProcessFunction) 低阶(更底层)流处理算子，实现自定义的业务逻辑

    value.print("prefix")

    try {
      env.execute()
    } catch {
      case exception: Exception => println(exception)
    }

  }

}
