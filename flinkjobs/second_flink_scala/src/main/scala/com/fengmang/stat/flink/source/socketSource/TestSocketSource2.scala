package com.fengmang.stat.flink.source.socketSource

import java.util.Date

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/14 10:55
 *
 */
object TestSocketSource2 {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    import org.apache.flink.api.scala._

    val value: DataStream[String] = env.socketTextStream("192.168.52.13", 9999)


    value.filter(!_.isEmpty).flatMap(_.split(",")).map((_, new Date().getTime))
      .assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[(String, Long)] {     //生成watermark

        var currentMaxTimestamp = 0L
        val maxOutOfOrderness = 10000L

        override def getCurrentWatermark: Watermark = {

          val watermark = new Watermark(maxOutOfOrderness - currentMaxTimestamp)

          println(watermark)

          watermark

        }

        override def extractTimestamp(t: (String, Long), l: Long): Long = {
          //当前元素的时间戳
          val timestamp: Long = t._2

          currentMaxTimestamp= Math.max(timestamp, l)

          timestamp
        }
      }).keyBy(0).window(TumblingEventTimeWindows.of(Time.seconds(3)))
        .allowedLateness(Time.seconds(1)).sum(1)
        /*.process(new ProcessWindowFunction[(String,Long),(String,Long),String,TimeWindow]{

          override def process(key: String,
                               context: ProcessWindowFunction[(String, Long), (String, Long), String, TimeWindow]#Context,
                               elements: lang.Iterable[(String, Long)],
                               out: Collector[(String, Long)]): Unit = {

            val it: util.Iterator[(String, Long)] = elements.iterator()

            while (it.hasNext) {


            }

          }
        })*/

    value.print("socket")

    try {
      env.execute()
    } catch {
      case exception: Exception => println(exception)
    }

  }

}
