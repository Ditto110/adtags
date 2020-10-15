package com.fengmang.stat.spark.test

import java.text.SimpleDateFormat
import java.util.Date

/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/15 15:29
 *
 */
object TestDate {
  def main(args: Array[String]): Unit = {

    println(toDate(1602746765000L))

  }

  def toDate(time: Long): String = {
    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = new Date(time)
    sdf.format(date)
  }

}
