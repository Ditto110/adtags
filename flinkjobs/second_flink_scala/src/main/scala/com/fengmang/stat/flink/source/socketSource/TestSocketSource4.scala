package com.fengmang.stat.flink.source.socketSource

import java.text.SimpleDateFormat
import java.util.{Date, Properties}

import com.fengmang.stat.flink.source.socketSource.TestSocketSource2.strfDate
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.functions.source.SourceFunction
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
object TestSocketSource4 {
  def main(args: Array[String]): Unit = {

    //设置环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment()

    import org.apache.flink.streaming.api.TimeCharacteristic
    //设置时间特性  基于事件还是基于process time,如果是even-time,则flink自动每200ms 刷新watermark
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)
    //接入kafka 数据源
    val prop = new Properties
    prop.setProperty("bootstrap.servers", "192.168.52.40:9092,192.168.52.41:9092")
    prop.setProperty("zookeeper.connect", "172.28.17.80")
    prop.setProperty("group.id", "default_group_id")
    val consumer = new FlinkKafkaConsumer010[String]("flink-test", new SimpleStringSchema, prop)

    consumer.setStartFromGroupOffsets()

    //设置数据源
    env.addSource(consumer)
      .filter(!_.isEmpty)
      .map(_.split(","))
      .map(e => ((e(0), 1), strpDate(e(1)).getTime))
      .assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[((String, Int), Long)] {

        var maxCurrentTimeStamp = 0L

        val lateness = 10000L

        var timeStamp = 0L

        var watermark: Watermark = _

        override def getCurrentWatermark: Watermark = {

          maxCurrentTimeStamp = Math.max(maxCurrentTimeStamp, timeStamp)

          watermark = new Watermark(maxCurrentTimeStamp)

//          println("watermark:" + new Date(watermark.getTimestamp))

          watermark



        }

        override def extractTimestamp(element: ((String, Int), Long), previousElementTimestamp: Long): Long = {


          println(s"elem:$element")

          timeStamp = element._2

          timeStamp
        }
      })
      .map(_._1)
      .keyBy(_._1)
      .timeWindow(Time.seconds(5))
      .process(new ProcessWindowFunction[(String,Int),(String,String),String,TimeWindow] {
        override def process(key: String, context: Context, elements: Iterable[(String, Int)], out: Collector[(String, String)]): Unit = {

          println(s"key:$key")
          println("current watermark:" + new Date(context.currentWatermark))
          println(s"window start:${strfDate(context.window.getStart)},end:${strfDate(context.window.getEnd)}")


          val iterator: Iterator[(String, Int)] = elements.iterator

          val tuples = new ArrayBuffer[(String, Int)]()

          while (iterator.hasNext) {

            val tuple: (String, Int) = iterator.next()

            tuples += tuple
          }


//          (tuples.head._1,tuples.sum)

        }
      })
//      .sum(1)
      /*.reduce((x, y) => {
        (x._1, x._2 + y._2)
      })*/
      //输出结果
      .addSink(x => {
        println(s"reduce result:$x")
      })
    //提交任务

    env.execute("word count")

  }


  def strpDate(time: String): Date = {

    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    val date: Date = sdf.parse(time)

    date

  }
}


