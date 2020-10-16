package com.fengmang.stat.flink.tableApiAndSql

import java.util.Random

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.table.api.scala._
import org.apache.flink.table.api.{EnvironmentSettings, Table, TableEnvironment}
import org.apache.flink.table.sources.CsvTableSource

/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/16 9:15
 *
 */
object TestRegisterTableMethod1 {

  val words = List("a", "b", "c", "d", "e")

  def main(args: Array[String]): Unit = {

    val params: ParameterTool = ParameterTool.fromArgs(args)

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val settings: EnvironmentSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build()

    val tEnv: StreamTableEnvironment = StreamTableEnvironment.create(env, settings)

    val table: Table = env.addSource(new SourceFunction[String] {

      private val random = new Random()

      override def run(ctx: SourceFunction.SourceContext[String]): Unit = {

        while (true) {

          val index: Int = random.nextInt(words.size - 1)

          ctx.collect(words(index))

          Thread.sleep(1000L)
        }
      }

      override def cancel(): Unit = ???

    }).map((_, 1)).toTable(tEnv, 'word, 'c)   //scala table-api语法，通过scala隐式转换使用(')标识一个字段

    tEnv.registerTable("wordcount", table)

/*    //通过table api方式查询
    val stream: DataStream[(Boolean, (String))] = tEnv.scan("wordcount").select('word)
      .toRetractStream //缩进模式返回的是boolean类型，表示数据追加或撤回*/


    //通过sql 方式查询
    val result: Table = tEnv.sqlQuery("select word,c from  wordcount")
    table.printSchema()

    val stream: DataStream[(Boolean, (String, Int))] = tEnv.toRetractStream[(String, Int)](result)

    stream.print("hello")

    env.execute()

  }

}
