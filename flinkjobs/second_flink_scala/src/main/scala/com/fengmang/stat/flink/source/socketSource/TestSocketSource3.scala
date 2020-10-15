package com.fengmang.stat.flink.source.socketSource

import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/14 10:55
 *
 */
object TestSocketSource3 {
  def main(args: Array[String]): Unit = {

    //设置环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment()
    //设置数据源
    env.addSource(new SourceFunction[String] {
      override def run(ctx: SourceFunction.SourceContext[String]): Unit = {
        while (true) {
          ctx.collect("hello hadoop hello storm hello spark")
          Thread.sleep(1000)
        }
      }

      override def cancel(): Unit = {}
    })
      //计算逻辑
      .flatMap(_.split(" "))
      .map((_, 1))
      .keyBy(_._1)
      .timeWindow(Time.seconds(10))
      .reduce((x, y) => {
        (x._1, x._2 + y._2)
      })
      //输出结果
      .addSink(x => {
        print(x + "\n")
      })
    //提交任务
    env.execute("word count")

  }
}


