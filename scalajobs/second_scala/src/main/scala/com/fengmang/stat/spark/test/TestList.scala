package com.fengmang.stat.spark.test

import scala.util.Random

/**
 *
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/12 19:46
 *
 */
object TestList {
  def main(args: Array[String]): Unit = {
    val list = List("a", "b", "c", "d", "e", "f")

    val random = new Random()
    val i: Int = random.nextInt(list.size)

    println(list(i))

  }

}
