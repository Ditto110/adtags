package com.fengmang.stat.flink.tableApiAndSql

import com.fengmang.stat.flink.tableApiAndSql.TestRegisterTableMethod2.list
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.table.api.scala.{StreamTableEnvironment, _}
import org.apache.flink.table.api.{EnvironmentSettings, Table}

import scala.util.Random


/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/16 17:02
 *
 */
object TestRegisterTableMethod3 {

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

    //方式二：注册表
    val table: Table = tEnv.fromDataStream(value, 'word, 'c)

    tEnv.registerTable("wordcount",table)

    val table1: Table = table.select("word")

    tEnv.toRetractStream[(String)](table1).print("hello")


    env.execute()

  }

}
