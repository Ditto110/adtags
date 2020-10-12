package com.fengmang.stat.flink

import org.apache.flink.api.java.aggregation.Aggregations
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.runtime.rest.messages.job.metrics.MetricsAggregationParameter.AggregationMode

/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/12 10:21
 *
 */
object WordCount {
  def main(args: Array[String]): Unit = {


    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

    import org.apache.flink.api.scala._

    val value: DataSet[String] = env.fromElements("a", "b", "c","a")

    //reduce 函数执行的功能和sum 相同 https://www.cosmozhu.fun/archives/236
    value.map((_,1)).groupBy(_._1).reduce((x,y)=>(x._1,x._2+y._2)).collect().foreach(println)

    //aggregation 语法糖
    value.map((_,1)).groupBy(0).sum(1).collect().foreach(println)
    value.map((_,1)).groupBy(0).aggregate(Aggregations.SUM,1).collect().foreach(println)


  }

}
