package com.fengmang.stat.flink.accumulator

import org.apache.flink.api.common.accumulators.IntCounter
import org.apache.flink.api.common.functions.{RichMapFunction, RuntimeContext}
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/13 10:09
 * 累加器api,job 执行结束后可以获取到最终结果
 *
 */
object TestAccumulator {

  def main(args: Array[String]): Unit = {

    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

    import org.apache.flink.api.scala._

    env.fromElements("a", "b", "c", "d").map(new RichMapFunction[String, (String, Int)] {

      //累加器
      private val counter = new IntCounter()

      override def open(parameters: Configuration): Unit = {
        //向上下文中添加一个累加器
        getRuntimeContext.addAccumulator("acc_cal", counter)
      }

      override def map(in: String): (String, Int) = {
        counter.add(1)
        (in, 1)
      }
    }).groupBy(0).sum(1).collect().foreach(println)


    val value: Int = env.getLastJobExecutionResult.getAccumulatorResult[Int]("acc_cal")

    println(s"acc result:$value")

  }

}
