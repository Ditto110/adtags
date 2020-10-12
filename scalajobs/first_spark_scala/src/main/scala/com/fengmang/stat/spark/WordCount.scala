package com.fengmang.stat.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

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

    val conf: SparkConf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[*]")

    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val sc: SparkContext = spark.sparkContext

    sc.setLogLevel("warn")

    val rdd: RDD[String] = sc.parallelize(Seq("a", "b", "c","a"))

    rdd.map((_,1)).reduceByKey(_+_).foreach(println)



    spark.close()

  }

}
