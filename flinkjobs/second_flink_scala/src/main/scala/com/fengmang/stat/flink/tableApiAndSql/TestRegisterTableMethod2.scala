package com.fengmang.stat.flink.tableApiAndSql

import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.table.api.scala.StreamTableEnvironment

import scala.util.Random
import org.apache.flink.api.scala._
import org.apache.flink.table.api.scala._


/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/16 17:02
 *
 */
object TestRegisterTableMethod2 {

  private val list = List("java", "scala", "python")

  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val settings: EnvironmentSettings = EnvironmentSettings.newInstance().inStreamingMode().useOldPlanner().build()

    val tEnv: StreamTableEnvironment = StreamTableEnvironment.create(env, settings)


    val value: DataStream[(String, Int)] = env.addSource(new SourceFunction[String] {

      private val random = new Random()

      override def run(ctx: SourceFunction.SourceContext[String]): Unit = {

        while (true) {

          val index: Int = random.nextInt(list.size - 1)

          ctx.collect(list(index))

          Thread.sleep(1000L)

        }
      }

      override def cancel(): Unit = ???
    }).map((_, 1))

    //方式一：注册表
    value.toTable(tEnv,'word,'c)  //转换为表

    tEnv.registerDataStream("wordcount",value)  //不设置具体字段名c

    tEnv.registerDataStream("wordcount",value,'word, 'c) //标识字段

    val table: Table = tEnv.scan("wordcount").select("word")  //或者字符串形式 "myLong"

    env.execute()

  }

}
