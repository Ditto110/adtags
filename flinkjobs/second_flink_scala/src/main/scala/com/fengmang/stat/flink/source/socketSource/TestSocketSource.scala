package com.fengmang.stat.flink.source.socketSource

import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.util.Collector

/**
 *
 * 获取socket 数据源
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/14 10:55
 *
 */
object TestSocketSource {

  import org.apache.flink.api.scala._

  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val dataDS: DataStream[String] = env.socketTextStream("192.168.52.13", 9999)

    val mapDS: DataStream[(String, Long, Int)] = dataDS.map(data => {
      val datas: Array[String] = data.split(",")
      (datas(0), datas(1).toLong, datas(2).toInt)
    })

    mapDS.keyBy(0).process(
      new KeyedProcessFunction[Tuple, (String, Long, Int), (String, Int)] {

        private var alarm = 0

        /**
         * 流中的每个元素都会调用此方法
         *
         * @param value 当前元素
         * @param ctx   当前元素上下文，可以h获取到key,定时器
         * @param out   输出
         */
        override def processElement(value: (String, Long, Int), ctx: KeyedProcessFunction[Tuple, (String, Long, Int), (String, Int)]#Context, out: Collector[(String, Int)]): Unit = {

          out.collect((value._1, value._3))

          alarm = value._3

          if (alarm > 10) {
            ctx.timerService().registerProcessingTimeTimer(alarm)
          }
        }

        override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[Tuple, (String, Long, Int), (String, Int)]#OnTimerContext, out: Collector[(String, Int)]): Unit = {
          //定时器回调函数
          out.collect(("onTimer", 1))
        }
      }).print()


    env.execute()
  }

}
