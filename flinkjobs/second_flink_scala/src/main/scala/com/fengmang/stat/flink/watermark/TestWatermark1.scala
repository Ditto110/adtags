package com.fengmang.stat.flink.watermark

import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.table.sources.wmstrategies.PeriodicWatermarkAssigner

import scala.util.Random

object TestWatermark1 {

  def main(args: Array[String]): Unit = {
    //http://www.hnbian.cn/posts/dfb2174e.html
    //https://blog.csdn.net/weixin_42412645/article/details/93378738

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    val input: DataStream[(String, Long)] = env.fromCollection(List(("a", 1L), ("b", 1L), ("b", 5L), ("b", 5L)))
    val timeWindow: DataStream[(String, Long)] = input.assignAscendingTimestamps(t => t._2)
    val result: DataStream[(String, Long)] = timeWindow.keyBy(0).timeWindow(Time.milliseconds(4)).sum("_2")

    result.print()

    env.execute()

  }

}
