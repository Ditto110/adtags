package com.fengmang.stat.flink.source.customerSource

import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

import scala.util.Random

/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/12 19:23
 *
 */
object TestMakeSource {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    import org.apache.flink.api.scala._


    val value: DataStream[String] = env.addSource(new SourceFunction[String] {
      override def run(sourceContext: SourceFunction.SourceContext[String]): Unit = {

        val list = List("a", "b", "c", "d", "e", "f")
        val random = new Random()
        val size: Int = list.size

        while (true) {

          val index: Int = random.nextInt(size)

          Thread.sleep(1000)

          sourceContext.collect(list(index))
        }
      }

      override def cancel(): Unit = {}
    })

//    env.setParallelism(1)
    value.map((_,1)).keyBy(0).sum(1).print()

    env.execute()

  }

}
