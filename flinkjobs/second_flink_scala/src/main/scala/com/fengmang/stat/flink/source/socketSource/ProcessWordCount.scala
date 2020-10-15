package com.fengmang.stat.flink.source.socketSource

//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/15 11:05
 *
 * window 处理函数 https://blog.csdn.net/qq_31866793/article/details/100139284
 *  ProcessWindowFunction
 *  ReduceFunction
 *  AggregateFunction
 *  FoldFunction
 *
 */
object ProcessWordCount {
  def main(args: Array[String]): Unit = {
    //设置环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment()

    import org.apache.flink.api.scala._

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
      .timeWindow(Time.seconds(10), Time.seconds(10))
      //调用process 方法获取具体的处理逻辑
      .process(new ProcessWindowFunction[(String, Int), (String, Int), String, TimeWindow] {
        override def process(key: String, context: Context, elements: Iterable[(String, Int)], out: Collector[(String, Int)]): Unit = {
          var value = 0;
          elements.foreach(kv => {
            value = value + kv._2
          })
          out.collect(key, value)
        }
      })
      .print().setParallelism(1)
    env.execute("word count")
  }
}
