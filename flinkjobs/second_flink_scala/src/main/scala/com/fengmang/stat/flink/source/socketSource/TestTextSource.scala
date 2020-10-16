package com.fengmang.stat.flink.source.socketSource

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala.typeutils.Types
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.table.api.{EnvironmentSettings, Table}
import org.apache.flink.table.api.scala.StreamTableEnvironment
import org.apache.flink.table.sources.CsvTableSource
import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem
import org.apache.flink.table.sinks.{CsvTableSink, TableSink}
import org.apache.flink.types.Row


/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/16 17:47
 *
 */
object TestTextSource {

  def main(args: Array[String]): Unit = {


    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val settings: EnvironmentSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build()

    val tEnv: StreamTableEnvironment = StreamTableEnvironment.create(env, settings)

    //读取文件
    val csvTableSource = new CsvTableSource("file:///C:\\Users\\sdt14325\\Desktop\\in\\flink\\words.txt", Array("name", "count"), Array(Types.STRING, Types.INT))
    tEnv.registerTableSource("wordcount", csvTableSource)

    val table: Table = tEnv.scan("wordcount").select("*")

    //输出
    val value: DataStream[(Boolean, (String, Int))] = tEnv.toRetractStream[(String, Int)](table)
    value.print()


    //写入到文件
    val csvSink: TableSink[Row] = new CsvTableSink("file:///C:\\Users\\sdt14325\\Desktop\\out\\flink\\words.txt", "\t", 1, FileSystem.WriteMode.OVERWRITE)
      .configure(Array("name","co"),Array(Types.STRING,Types.INT))

    tEnv.registerTableSink("csvSink", csvSink)
    table.insertInto("csvSink")



    env.execute()

  }

}
