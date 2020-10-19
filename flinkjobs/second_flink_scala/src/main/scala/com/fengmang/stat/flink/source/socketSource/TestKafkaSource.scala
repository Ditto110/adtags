package com.fengmang.stat.flink.source.socketSource

import java.text.SimpleDateFormat
import java.util.{Date, Properties}

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.functions.{AssignerWithPeriodicWatermarks, KeyedProcessFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.util.Collector

import scala.collection.mutable.ArrayBuffer

/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/14 10:55
 *
 */
object TestKafkaSource {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

//    env.enableCheckpointing(100L)
    import org.apache.flink.api.scala._

    val prop = new Properties
    prop.setProperty("bootstrap.servers", "192.168.52.40:9092,192.168.52.41:9092")
    prop.setProperty("zookeeper.connect", "172.28.17.80")
    prop.setProperty("group.id", "default_group_id")
    val consumer = new FlinkKafkaConsumer010[String]("flink-test", new SimpleStringSchema, prop)

    consumer.setStartFromGroupOffsets()

    env.addSource(consumer)
      .filter(!_.isEmpty)
      .map(f => {
        val arr = f.split(",")
        val code = arr(0)
        val time = parseDateNewFormat(arr(1)).getTime
        //        val time = arr(1).toLong
        (code, time)
      })
      .assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[(String, Long)] {

        var currentMaxTimestamp = 0L
        val maxOutOfOrderness = 10000L // 最大允许的乱序时间是10s
        var a: Watermark = null
        val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

        //Returns the current watermark
        override def getCurrentWatermark: Watermark = {
          a = new Watermark(currentMaxTimestamp - maxOutOfOrderness)
          a
        }

        //Assigns a timestamp to an element, in milliseconds since the Epoch.
        override def extractTimestamp(element: (String, Long), previousElementTimestamp: Long): Long = {

          currentMaxTimestamp = Math.max(currentMaxTimestamp, element._2)

          println("timestamp:" + element._1 + "," + element._2 + "," + sdf.format(element._2) + " ||| "
            + currentMaxTimestamp + "," + sdf.format(currentMaxTimestamp) + " ||| "
            + new Date(getNewFormatDateString(a)))

          element._2
        }
      })
      .keyBy(_._1)
      .timeWindow(Time.seconds(5))
      .process(new ProcessWindowFunction[(String, Long), String, String, TimeWindow] {
        override def process(key: String, context: Context, elements: Iterable[(String, Long)], out: Collector[String]): Unit = {

          val tuples = new ArrayBuffer[(String, Long)]()

          println(s"key:$key")
          println(s"window start:${strfDate(context.window.getStart)},end:${strfDate(context.window.getEnd)}")
          println(s"currentWatermark:${context.currentWatermark}")

          val it: Iterator[(String, Long)] = elements.iterator
          while (it.hasNext) {
            val tuple: (String, Long) = it.next()
            tuples += tuple
          }
          out.collect(tuples.head._2.toString)
        }
      }).print()

    env.execute()

  }

  def parseDateNewFormat(t: String): Date = {

    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    val date: Date = sdf.parse(t)

    date
  }

  def getNewFormatDateString(a: Watermark) = {

    a match {
      case x if x == null => 0L
      case _ => a.getTimestamp
    }
  }

  def strfDate(time: Long): String = {
    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = new Date(time)
    sdf.format(date)
  }

}
